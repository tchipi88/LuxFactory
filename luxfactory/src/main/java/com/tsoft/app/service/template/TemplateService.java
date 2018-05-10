/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.service.template;




import com.tsoft.app.domain.PersistentAuditEvent;
import com.tsoft.app.domain.PersistentToken;
import com.tsoft.app.domain.Privilege;
import com.tsoft.app.domain.Role;
import com.tsoft.app.domain.User;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

/**
 *
 * @author tchipi
 */
@Service
public class TemplateService {

    private final Logger log = LoggerFactory.getLogger(TemplateService.class);

    @Autowired
    private SpringTemplateEngine templateEngine;
    @Autowired
    private EntityManager em;
    @Autowired
    private ResourceLoader resourceLoader;

    public void processTemplateEngine(String basePackage) throws Exception {
        log.debug("Start entity templates Rendering");
        cleanUpDirectories();
        String index = "";
        Metamodel metamodel = em.getMetamodel();
        for (EntityType et : metamodel.getEntities()) {
            if (et.getJavaType().equals(User.class)
                    || et.getJavaType().equals(Privilege.class)
                   || et.getJavaType().equals(Role.class)
                    || et.getJavaType().equals(PersistentToken.class)
                    || et.getJavaType().equals(PersistentAuditEvent.class)) {
                continue;
            }

            Context context = new Context();
            context.setVariable("entity", et.getJavaType().getSimpleName());
            context.setVariable("entity_var", et.getJavaType().getSimpleName().toLowerCase().substring(0, 1).concat(et.getJavaType().getSimpleName().substring(1)));
            context.setVariable("entity_url", entityUrl(et.getJavaType().getSimpleName()));
            context.setVariable("entityid", et.getIdType().getJavaType().getSimpleName());
            context.setVariable("entitypackage", et.getJavaType().getCanonicalName());

            context.setVariable("repositorypackage", basePackage.concat(".repository"));
            context.setVariable("searchrepositorypackage", basePackage.concat(".repository.search"));
            context.setVariable("controllerpackage", basePackage.concat(".web.rest"));

            creatingEntityRepositories(context);
            if (et.getJavaType().isAnnotationPresent(Document.class)) {
                creatingEntitySearchRepositories(context);
            }
            creatingEntityController(context, et.getJavaType().isAnnotationPresent(Document.class));
            creatingEntityClient(context, et);

            index += "  <script src=\"tpl/entities/" + entityUrl(et.getJavaType().getSimpleName()) + "/" + entityUrl(et.getJavaType().getSimpleName()) + "-dialog.controller.js\"></script>\n"
//                    + "        <script src=\"tpl/entities/" + entityUrl(et.getJavaType().getSimpleName()) + "/" + entityUrl(et.getJavaType().getSimpleName()) + "-detail.controller.js\"></script>\n"
                    + "        <script src=\"tpl/entities/" + entityUrl(et.getJavaType().getSimpleName()) + "/" + entityUrl(et.getJavaType().getSimpleName()) + "-delete-dialog.controller.js\"></script>\n"
                    + "        <script src=\"tpl/entities/" + entityUrl(et.getJavaType().getSimpleName()) + "/" + entityUrl(et.getJavaType().getSimpleName()) + ".state.js\"></script>\n"
                    + "        <script src=\"tpl/entities/" + entityUrl(et.getJavaType().getSimpleName()) + "/" + entityUrl(et.getJavaType().getSimpleName()) + ".service.js\"></script>\n"
                    + ((et.getJavaType().isAnnotationPresent(Document.class)) ? "        <script src=\"tpl/entities/" + entityUrl(et.getJavaType().getSimpleName()) + "/" + entityUrl(et.getJavaType().getSimpleName()) + ".search.service.js\"></script>\n" : "")
                    + "        <script src=\"tpl/entities/" + entityUrl(et.getJavaType().getSimpleName()) + "/" + entityUrl(et.getJavaType().getSimpleName()) + ".controller.js\"></script>\n";
        }
        creatingFiles(index, "index.html");
        log.debug("End entity templates Rendering");
    }

    public void creatingEntityClient(Context context, EntityType et) throws Exception {
        log.debug("Creating " + (String) context.getVariable("entity") + " Client Files");
        creatingEntityClientPart(context, et, "-delete-dialog.controller.js");
        creatingEntityClientPart(context, et, "-delete-dialog.html");

        context.setVariable("entity_selects_header", entitySelectsHeader(et));
        context.setVariable("entity_selects_header1", entitySelectsHeader1(et));
        context.setVariable("entity_selects", entitySelects(et));
        context.setVariable("entity_dates", entityDates(et));
        creatingEntityClientPart(context, et, "-dialog.controller.js");

        context.setVariable("form", entityForm(et));
        creatingEntityClientPart(context, et, "-dialog.html");

        context.setVariable("entity_detail", entityDetail(et));
       // creatingEntityClientPart(context, et, "-detail.html");
       // creatingEntityClientPart(context, et, "-detail.controller.js");

        if (et.getJavaType().isAnnotationPresent(Document.class)) {
            creatingEntityClientController(context, et, ".controllerSearch.js");
        } else {
            creatingEntityClientController(context, et, ".controller.js");
        }

        if (et.getJavaType().isAnnotationPresent(Document.class)) {
            creatingEntityClientPart(context, et, ".search.service.js");
        }
        if (hasLocalDate(et)) {
            context.setVariable("entity_conertDateToServer", entityDatesForServices(et).get(0));
            context.setVariable("entity_convertDateFromServer", entityDatesForServices(et).get(1));
            creatingEntityClientServices(context, et, ".serviceDate.js");
        } else {
            creatingEntityClientServices(context, et, ".service.js");
        }

        creatingEntityClientPart(context, et, ".state.js");

        context.setVariable("tableHeader", entityTableHeader(et));
        context.setVariable("tableBody", entityTableBody(et));
        if (et.getJavaType().isAnnotationPresent(Document.class)) {
            context.setVariable("search", " <div class=\"panel-heading\">\n"
                    + "            <div class=\"row\">\n"
                    + "                <div class=\"col-xs-12 no-padding-right\">\n"
                    + "                    <form name=\"searchForm\" class=\"form-inline\">\n"
                    + "                        <div class=\"input-group pull-right\" >\n"
                    + "                            <input type=\"text\" class=\"form-control\" ng-model=\"vm.searchQuery\" id=\"searchQuery\" placeholder=\"Recherche\">\n"
                    + "                            <span  class=\"input-group-btn width-min\" >\n"
                    + "                                <button class=\"btn btn-info\" ng-click=\"vm.search(vm.searchQuery)\">\n"
                    + "                                    <span class=\"glyphicon glyphicon-search\"></span>\n"
                    + "                                </button>\n"
                    + "                            </span>\n"
                    + "                            <span class=\"input-group-btn width-min\" ng-if=\"vm.currentSearch\">\n"
                    + "                                <button class=\"btn btn-info\" ng-click=\"vm.clear()\">\n"
                    + "                                    <span class=\"glyphicon glyphicon-trash\"></span>\n"
                    + "                                </button>\n"
                    + "                            </span>\n"
                    + "                        </div>\n"
                    + "                    </form>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>");
        }

        creatingEntityClientPart(context, et, "s.html");

    }

    public void creatingEntityClientController(Context context, EntityType et, String file) throws Exception {
        String content = templateEngine.process("entity" + file, context);
        creatingFiles(content, "views" + File.separator + context.getVariable("entity_url") + File.separator + context.getVariable("entity_url") + ".controller.js");

    }

    public void creatingEntityClientServices(Context context, EntityType et, String file) throws Exception {
        String content = templateEngine.process("entity" + file, context);
        creatingFiles(content, "views" + File.separator + context.getVariable("entity_url") + File.separator + context.getVariable("entity_url") + ".service.js");

    }

    public void creatingEntityClientPart(Context context, EntityType et, String file) throws Exception {
        String content = templateEngine.process("entity" + file, context);
        creatingFiles(content, "views" + File.separator + context.getVariable("entity_url") + File.separator + context.getVariable("entity_url") + file);

    }

    public void creatingEntityRepositories(Context context) throws Exception {
        log.debug("Creating " + (String) context.getVariable("entity") + " repositories");
        String content = templateEngine.process("TemplateRepository.txt", context);
        creatingFiles(content, "repository" + File.separator + (String) context.getVariable("entity") + "Repository.java");
    }

    public void creatingEntitySearchRepositories(Context context) throws Exception {
        log.debug("Creating " + (String) context.getVariable("entity") + " Searchrepositories");
        String content = templateEngine.process("TemplateSearchRepository.txt", context);
        creatingFiles(content, "searchrepository" + File.separator + (String) context.getVariable("entity") + "SearchRepository.java");
    }

    public void creatingEntityController(Context context, boolean search) throws Exception {
        log.debug("Creating " + (String) context.getVariable("entity") + " controllers ");
        String content = templateEngine.process("TemplateController" + (search ? "Search" : "") + ".txt", context);
        creatingFiles(content, "controller" + File.separator + (String) context.getVariable("entity") + "Resource.java");
    }

    public void creatingFiles(String content, String filename) throws Exception {
        Resource dir = resourceLoader.getResource("classpath:templates");
        // create file-in-subdirectory path
        Path file = Paths.get(dir.getFilename() + File.separator + filename);

        if (!Files.exists(file.getParent())) {
            try {
                Files.createDirectory(file.getParent());
            } catch (IOException e) {
                System.err.println(e);
            }
        }
        //    deleteAllFilesDirOrCreateDir(Paths.get(dir.getFilename()), false);
        Files.write(file, content.getBytes());
    }

    public void cleanUpDirectories() throws IOException {
        Resource dir = resourceLoader.getResource("classpath:templates");
        deleteAllFilesDirOrCreateDir(Paths.get(dir.getFilename()), false);

    }

    public static String entityUrl(String inputString) {
        String result = "";
        if (inputString.length() == 0) {
            return result;
        }
        char firstChar = inputString.charAt(0);
        result += Character.toLowerCase(firstChar);
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                result += "-" + Character.toLowerCase(currentChar);
            } else {
                result += currentChar;
            }
        }
        return result;
    }

    public static String toLowerCase(String inputString) {
        String result = "";
        if (inputString.length() == 0) {
            return result;
        }
        char firstChar = inputString.charAt(0);
        char firstCharToLowerCase = Character.toLowerCase(firstChar);
        result += firstCharToLowerCase;
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char currentCharToLowerCase = Character.toLowerCase(currentChar);
            result += currentCharToLowerCase;
        }
        return result;
    }

    public static String toCamelCase(String inputString) {
        String result = "";
        if (inputString.length() == 0) {
            return result;
        }
        char firstChar = inputString.charAt(0);
        char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result += firstCharToUpperCase;
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char previousChar = inputString.charAt(i - 1);
            if (previousChar == ' ') {
                char currentCharToUpperCase = Character.toUpperCase(currentChar);
                result += currentCharToUpperCase;
            } else {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result += currentCharToLowerCase;
            }
        }
        return result;
    }

    public static void deleteAllFilesDirOrCreateDir(Path dir, boolean deletedir) throws IOException {
        if (Files.exists(dir)) {
            deleteAllFilesDir(dir, deletedir);
        } else {
            Files.createDirectories(dir);
        };
    }

    public static void deleteAllFilesDir(Path dir, boolean deletedir) {
        try {
            Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                        throws IOException {
                    System.out.println("Deleting file: " + file);
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir,
                        IOException exc) throws IOException {
                    if (deletedir) {
                        System.out.println("Deleting dir: " + dir);
                        if (exc == null) {
                            Files.delete(dir);
                            return FileVisitResult.CONTINUE;
                        } else {
                            throw exc;
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String entityDates(EntityType et) throws Exception {
        String result = "";
        List<Field> inputs = new ArrayList();
        ReflectionUtils.doWithFields(et.getJavaType(), (field) -> {
            inputs.add(field);
        }, (java.lang.reflect.Field f)
                -> f.getType().equals(LocalDate.class)
        );
        for (Field f : inputs) {
            result += " vm.datePickerOpenStatus." + f.getName() + " = false;\n";
        }
        return result;
    }

    private boolean hasLocalDate(EntityType et) throws Exception {
        List<Field> inputs = new ArrayList();
        ReflectionUtils.doWithFields(et.getJavaType(), (field) -> {
            inputs.add(field);
        }, (java.lang.reflect.Field f)
                -> f.getType().equals(LocalDate.class)
        );
        return !inputs.isEmpty();
    }

    private List<String> entityDatesForServices(EntityType et) throws Exception {
        String r = "";
        List<Field> inputs = new ArrayList();
        List<String> result = new ArrayList();
        ReflectionUtils.doWithFields(et.getJavaType(), (field) -> {
            inputs.add(field);
        }, (java.lang.reflect.Field f)
                -> f.getType().equals(LocalDate.class)
        );
        for (Field f : inputs) {
            r += " copy." + f.getName() + " =DateUtils.convertLocalDateToServer(copy." + f.getName() + ");\n";
        }
        result.add(r);
        r = "";
        for (Field f : inputs) {
            r += " data." + f.getName() + " =DateUtils.convertLocalDateFromServer(data." + f.getName() + ");\n";
        }
        result.add(r);
        r = "";
        return result;
    }

    private String entitySelectsHeader(EntityType et) throws Exception {
        String result = "";
        Set<String> inputs = new HashSet();
        ReflectionUtils.doWithFields(et.getJavaType(), (field) -> {
            inputs.add(field.getType().getSimpleName());
        }, (java.lang.reflect.Field f)
                -> FieldUtils.getInputType(f).equals(InputType.SELECT)
        );
        for (String f : inputs) {
            result += ",'" + f + "'";
        }
        return result;
    }

    private String entitySelectsHeader1(EntityType et) throws Exception {

        String result = "";
        Set<String> inputs = new HashSet();
        ReflectionUtils.doWithFields(et.getJavaType(), (field) -> {
            inputs.add(field.getType().getSimpleName());
        }, (java.lang.reflect.Field f)
                -> FieldUtils.getInputType(f).equals(InputType.SELECT)
        );
        for (String f : inputs) {
            result += "," + f;
        }
        return result;
    }

    private String entitySelects(EntityType et) throws Exception {
        String result = "";
        List<Field> inputs = new ArrayList();
        ReflectionUtils.doWithFields(et.getJavaType(), (field) -> {
            inputs.add(field);
        }, (java.lang.reflect.Field f)
                -> FieldUtils.getInputType(f).equals(InputType.SELECT)
        );
        for (Field f : inputs) {
            result += "vm." + f.getName().toLowerCase() + "s = " + f.getType().getSimpleName() + ".query();\n";
        }
        return result;
    }

    public String entityForm(EntityType et) throws Exception {
        String entity = et.getJavaType().getSimpleName().toLowerCase().substring(0, 1).concat(et.getJavaType().getSimpleName().substring(1));
        String result = "";
        for (Field f : ClassUtils.getFields(et.getJavaType())) {
            result += " <div class=\"col-sm-6\">\n";
            result += " <div class=\"form-group\">\n";
            result += " <label class=\"control-label\"  for=\"field_" + f.getName() + "\">" + FieldUtils.coollabel(f.getName()) + ""
                    + (FieldUtils.isRequiredField(f) ? " <span style=\"color:red\" >*</span>" : "")
                    + "</label>\n";
            switch (FieldUtils.getInputType(f)) {
                case TEXT:
                    result += "  <input  class=\"form-control\"   type=\"text\"  autocomplete=\"off\"   id=\"field_" + f.getName() + "\" ng-model=\"vm." + entity + "." + f.getName() + "\" "
                            + "                    ng-readonly=\"" + FieldUtils.isReadOnlyField(f) + "\" "
                            + " ng-required=\"" + FieldUtils.isRequiredField(f) + "\" "
                            + (FieldUtils.getMinLenth(f) != null ? "   ng-minlength=\"" + FieldUtils.getMinLenth(f) + "\"  " : "")
                            + (FieldUtils.getMaxLength(f) != null ? "   ng-maxlength=\"" + FieldUtils.getMaxLength(f) + "\"  " : "")
                            + "                    />\n";
                    break;

                case EMAIL:
                    result += "  <div class=\"input-group\"> <input  class=\"form-control\"   type=\"email\"  autocomplete=\"off\"  id=\"field_" + f.getName() + "\"  ng-model=\"vm." + entity + "." + f.getName() + "\" "
                            + "                    ng-readonly=\"" + FieldUtils.isReadOnlyField(f) + "\" "
                            + " ng-required=\"" + FieldUtils.isRequiredField(f) + "\" "
                            + "                    /><span class=\"input-group-addon\" >  <span >\n"
                            + "                        <i class=\"fa fa-envelope\"></i>\n"
                            + "                    </span> </span>\n"
                            + "        </div>\n";
                    break;
                case TEL:
                    result += "  <div class=\"input-group\"> <input  class=\"form-control\"   type=\"text\"  autocomplete=\"off\"  id=\"field_" + f.getName() + "\"  ng-model=\"vm." + entity + "." + f.getName() + "\" "
                            + "                    ng-readonly=\"" + FieldUtils.isReadOnlyField(f) + "\" "
                            + " ng-required=\"" + FieldUtils.isRequiredField(f) + "\" "
                            + "                    /><span class=\"input-group-addon\" >  <span >\n"
                            + "                        <i class=\"fa fa-phone\"></i>\n"
                            + "                    </span> </span>\n"
                            + "        </div>\n";
                    break;
                case NUMBER:
                    result += "  <input  class=\"form-control\"   type=\"number\"  autocomplete=\"off\"  id=\"field_" + f.getName() + "\" ng-model=\"vm." + entity + "." + f.getName() + "\" "
                            + "                    ng-readonly=\"" + FieldUtils.isReadOnlyField(f) + "\" "
                            + " ng-required=\"" + FieldUtils.isRequiredField(f) + "\" "
                            + (FieldUtils.getMin(f) != null ? "   ng-min=\"" + FieldUtils.getMin(f) + "\"  " : "")
                            + (FieldUtils.getMax(f) != null ? "   ng-max=\"" + FieldUtils.getMax(f) + "\"  " : "")
                            + "                    />\n";
                    break;
                case TEXTAREA:
                    result += "   <textarea class=\"form-control\" rows=\"3\"  cols=\"30\"   id=\"field_" + f.getName() + "\" ng-model=\"vm." + entity + "." + f.getName() + "\" "
                            + "                    ng-readonly=\"" + FieldUtils.isReadOnlyField(f) + "\" "
                            + " ng-required=\"" + FieldUtils.isRequiredField(f) + "\" "
                            + "                    > </textarea>\n";
                    break;
                case CHECKBOX:
                    result += " <div class=\"form-control\">\n"
                            + "            <label class=\"i-checks\">\n"
                            + "                <input type=\"checkbox\" checked  ng-model=\"vm." + entity + "." + f.getName() + "\"   id=\"field_" + f.getName() + "\"  name=\"" + f.getName() + "\" /><i></i> \n"
                            + "            </label>\n"
                            + "        </div>\n";
                    break;
                case FILE:
                    break;
                case IMAGE:
                    result
                            += "        <div ngf-drop ngf-change=\"vm.setMimage($file, '" + f.getName() + "')\" ngf-pattern=\"'image/*'\"    ngf-capture=\"'camera'\">\n"
                            + "            <img data-ng-src=\"{{'data:' + vm." + entity + "." + f.getName() + "ContentType  + ';base64,' + vm." + entity + "." + f.getName() + "}}\" style=\"max-height: 100px;\" ng-if=\"vm." + entity + "." + f.getName() + "\"/>\n"
                            + "            <div ng-if=\"vm." + entity + "." + f.getName() + "\" class=\"help-block clearfix\">\n"
                            + "                <span class=\"pull-left\">{{vm." + entity + "." + f.getName() + "ContentType}}, {{byteSize(vm." + entity + "." + f.getName() + ")}}</span>\n"
                            + "                <button ng-click=\"vm." + entity + "." + f.getName() + " = null;vm." + entity + "." + f.getName() + "ContentType = null;\"\n"
                            + "                        class=\"btn btn-default btn-xs pull-right\">\n"
                            + "                    <span class=\"glyphicon glyphicon-remove\"></span>\n"
                            + "                </button>\n"
                            + "            </div>\n"
                            + "            <button type=\"file\" ngf-select class=\"btn btn-default btn-block\"\n"
                            + "                    ngf-change=\"vm.setMimage($file,  '" + f.getName() + "')\" accept=\"image/*\" >\n"
                            + "                Add image\n"
                            + "            </button>\n"
                            + "        </div>\n"
                            + "        <input type=\"hidden\" class=\"form-control\" id=\"field_" + f.getName() + "\"  name=\"" + f.getName() + "\"\n"
                            + "               ng-model=\"vm." + entity + "." + f.getName() + "\"      minbytes=\"5\" />\n"
                            + "        <input type=\"hidden\" class=\"form-control\" name=\"" + f.getName() + "ContentType\" id=\"" + f.getName() + "ContentType\"\n"
                            + "               ng-model=\"vm." + entity + "." + f.getName() + "ContentType\" />\n"
                            + "\n";
                    break;
                case SELECT:
                    String manytoone = f.getName().toLowerCase();
                    String templCtrl = (f.getType().getSimpleName());
                    String templUrl = entityUrl(f.getType().getSimpleName());
                    String display = ClassUtils.getFieldlibelle(f.getType()).getName();
                    result += " <div class=\"input-group\">\n"
                            + "                        <ui-select ng-model=\"vm." + entity + "." + f.getName() + "\"    ng-required=\"" + FieldUtils.isRequiredField(f) + "\">\n"
                            + "                            <ui-select-match placeholder=\"Select " + f.getType().getSimpleName() + "\">{{$select.selected." + display + "}}</ui-select-match>\n"
                            + "                            <ui-select-choices repeat=\"" + manytoone + " in vm." + manytoone + "s | filter: $select.search\">\n"
                            + "                                <div ng-bind-html=\"" + manytoone + "." + display + " | highlight: $select.search\"></div>\n"
                            + "                            </ui-select-choices>\n"
                            + "                        </ui-select>  \n"
                            + "                        <span class=\"input-group-btn\" >\n"
                            + "                            <button   ng-click=\"vm.zoomColumn('" + templCtrl + "','" + templUrl + "','" + f.getName() + "', vm." + entity + "." + f.getName() + ")\" \n"
                            + "                                      class=\"btn btn-default\" type=\"button\"  ng-hide=\"!vm." + entity + "." + f.getName() + "\">\n"
                            + "                                <span class=\"fa fa-eye\"></span>\n"
                            + "                            </button>\n"
                            + "                            <button ng-click=\"vm.zoomColumn('" + templCtrl + "', '" + templUrl + "','" + f.getName() + "', {})\"      \n"
                            + "                                    class=\"btn btn-default\" type=\"button\"  ng-hide=\"vm." + entity + "." + f.getName() + "\">\n"
                            + "                                <span class=\"fa fa-plus fw\"></span>\n"
                            + "                            </button>\n"
                            + "                        </span>\n"
                            + "                    </div>";
                    break;
                case SELECTENUM:
                    result += " <select class=\"form-control\" name=\"" + f.getName() + "\"  ng-model=\"vm." + entity + "." + f.getName() + "\"  id=\"field_" + f.getName() + "\" " + (FieldUtils.isRequiredField(f) ? "required" : "") + ">\n"
                            + FieldUtils.getSelectsEnumerated(f)
                            + "            </select>\n";
                    break;
                case DATE:
                    result += "  <div class=\"input-group\">\n"
                            + "                    <input  id=\"field_" + f.getName() + "\" type=\"text\" class=\"form-control\" name=\"" + f.getName() + "\"    ng-required=\"" + FieldUtils.isRequiredField(f) + "\"  uib-datepicker-popup=\"{{dateformat}}\"  ng-model=\"vm." + entity + "." + f.getName() + "\" "
                            + " is-open=\"vm.datePickerOpenStatus." + f.getName() + "\"\n"
                            + "                    />\n"
                            + "                    <span class=\"input-group-btn\">\n"
                            + "                        <button type=\"button\" class=\"btn btn-default\" ng-click=\"vm.openCalendar('" + f.getName() + "')\"><i class=\"glyphicon glyphicon-calendar\"></i></button>\n"
                            + "                    </span>\n"
                            + "                </div>\n";
                    break;
                default:
                    break;
            }
            result += " </div>\n";
            result += " </div>\n";
        }
        return result;
    }

    private String entityTableBody(EntityType et) throws Exception {
        String result = "", champ = "", filtre = "";
        for (Field f : ClassUtils.getFieldsToDisplayInTable(et.getJavaType())) {
            if (f.getType().equals(LocalDate.class)) {
                filtre = " | date:'mediumDate'";
            }
            if (f.getType().equals(BigDecimal.class)) {
                filtre = " | number ";
            }
            champ = et.getJavaType().getSimpleName().toLowerCase().substring(0, 1).concat(et.getJavaType().getSimpleName().substring(1)) + "." + (f.getName());
            if (FieldUtils.getInputType(f).equals(InputType.SELECT)) {
                champ += "." + ClassUtils.getFieldlibelle(f.getType()).getName();
            }
            result += " <td>{{";
            result += champ + filtre;
            result += "}}</td>\n";
            filtre = "";
        }
        return result;
    }

    private String entityDetail(EntityType et) throws Exception {
        String result = "";
        List<String> ths = new ArrayList();
        ReflectionUtils.doWithFields(et.getJavaType(), (field) -> {

            ths.add("<dt><span >" + FieldUtils.coollabel(field.getName()) + "</span></dt>\n"
                    + "        <dd>\n"
                    + "            <span>{{vm." + et.getJavaType().getSimpleName().toLowerCase().substring(0, 1).concat(et.getJavaType().getSimpleName().substring(1)) + "." + field.getName() + "}}</span>\n"
                    + "        </dd> ");
        }, (java.lang.reflect.Field f)
                -> !f.isAnnotationPresent(Id.class) && !f.isAnnotationPresent(CreatedBy.class) && !f.isAnnotationPresent(CreatedDate.class)
                && !f.isAnnotationPresent(LastModifiedBy.class) && !f.isAnnotationPresent(LastModifiedDate.class)
                && !f.getName().equalsIgnoreCase("serialVersionUID")
                && !f.getName().equalsIgnoreCase("$jacocoData")
        );
        result = ths.stream().collect(Collectors.joining("\n"));
        return result;
    }

    private String entityTableHeader(EntityType et) throws Exception {
        String result = "";
        for (Field field : ClassUtils.getFieldsToDisplayInTable(et.getJavaType())) {
            result += " <th jh-sort-by=\"" + field.getName() + "\"><span >" + FieldUtils.coollabel(field.getName()) + "</span> <span class=\"fa fa-sort\"></span></th>\n";
        }
        return result;
    }

}
