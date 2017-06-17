/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.service.template;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.Email;
import org.json.JSONArray;
import org.springframework.util.ReflectionUtils;

/**
 *
 * @author tchipi
 */
public class FieldUtils {

    public static String getSelectsEnumerated(Field f) {
        Method m = ReflectionUtils.findMethod(f.getType(), "values");
        Object[] options = (Object[]) ReflectionUtils.invokeMethod(m, null);
        String result = "";
        for (Object option : options) {
            result += "<option value=\"" + option + "\">" + option + "</option>\n";
        }
        return result;

    }

    public static boolean isRequiredField(Field f) {
        return (f.isAnnotationPresent(NotNull.class)
                || (f.isAnnotationPresent(Column.class)
                && !f.getAnnotation(Column.class).nullable())
                || (f.isAnnotationPresent(JoinColumn.class)
                && !f.getAnnotation(JoinColumn.class).nullable())
                || (f.isAnnotationPresent(Basic.class) && !f.getAnnotation(Basic.class).optional()));
    }

    public static Integer getMaxLength(Field f) {
        if (f.isAnnotationPresent(Size.class)) {
            return f.getAnnotation(Size.class).max();
        }
        if (f.isAnnotationPresent(Column.class)) {
            return f.getAnnotation(Column.class).length();
        }
        return 255;
    }

    public static Integer getMinLenth(Field f) {
        if (f.isAnnotationPresent(Size.class)) {
            return f.getAnnotation(Size.class).min();
        }
        return null;
    }

    public static Number getMax(Field f) {
        if (f.isAnnotationPresent(Max.class)) {
            return f.getAnnotation(Max.class).value();
        }
        if (f.getType().equals(Byte.class) || f.getType().getName().equals("byte")) {
            return Byte.MAX_VALUE;
        }
        if (f.getType().equals(Short.class) || f.getType().getName().equals("short")) {
            return Short.MAX_VALUE;
        }
        return null;
    }

    public static Number getMin(Field f) {
        if (f.isAnnotationPresent(Min.class)) {
            return f.getAnnotation(Min.class).value();
        }
        if (f.getType().equals(Byte.class) || f.getType().getName().equals("byte")) {
            return Byte.MIN_VALUE;
        }
        if (f.getType().equals(Short.class) || f.getType().getName().equals("short")) {
            return Short.MIN_VALUE;
        }
        return null;
    }

    public static boolean isReadOnlyField(Field f) {
        return ((f.isAnnotationPresent(Id.class) && f.isAnnotationPresent(GeneratedValue.class))
                || f.isAnnotationPresent(Formula.class)||f.isAnnotationPresent(ReadOnly.class));

    }

    public static String getlabelField(Field f) {

        if (f.isAnnotationPresent(Label.class)) {
            return FirstLetterCapital(f.getAnnotation(Label.class).value());
        } else {
            return coollabel(f.getName());
        }

    }

    public static String FirstLetterCapital(String string) {
        String result = string;
        result = string.toUpperCase().substring(0, 1).concat(string.substring(1));
        return result;

    }

    public static String coollabel(String label) {

        boolean firstPass = true;
        String separator = "";
        StringBuilder joined = new StringBuilder();

        for (String part : org.apache.commons.lang.StringUtils.splitByCharacterTypeCamelCase(label)) {
            if (part == null || part.trim().isEmpty()) {
                // skip null and space
                continue;
            }
            part = FirstLetterCapital(part);
            joined.append(separator).append(part);
            if (firstPass) {
                firstPass = false;
                separator = " ";
            }
        }
        return joined.toString();
    }

    public static InputType getInputType(Field f) {

        if (f.isAnnotationPresent(ManyToOne.class) || f.isAnnotationPresent(OneToOne.class)) {
            return InputType.SELECT;
        }

        if (f.isAnnotationPresent(Lob.class) && f.getType().equals(String.class)) {
            return InputType.TEXTAREA;
        }
        if (f.isAnnotationPresent(Image.class) && f.isAnnotationPresent(Lob.class)) {
            return InputType.IMAGE;
        }
        if (f.isAnnotationPresent(Fichier.class) && f.isAnnotationPresent(Lob.class)) {
            return InputType.FILE;
        }
        if (f.getType().equals(LocalDate.class)) {
            return InputType.DATE;
        }
        if (f.getType().equals(LocalDateTime.class) || f.getType().equals(ZonedDateTime.class)) {
            return InputType.TIMESTAMP;
        }
        if (f.getType().equals(LocalTime.class)) {
            return InputType.TIME;
        }
        if (f.getType().equals(Date.class)) {
            if (f.isAnnotationPresent(Temporal.class)) {
                if (f.getAnnotation(Temporal.class).value().equals(TemporalType.TIMESTAMP)) {
                    return InputType.TIMESTAMP;
                }
                if (f.getAnnotation(Temporal.class).value().equals(TemporalType.TIME)) {
                    return InputType.TIME;
                }
            }
            return InputType.DATE;
        }

        if (f.getName().startsWith("email") || f.isAnnotationPresent(Email.class)) {
            return InputType.EMAIL;
        }
        if (f.getName().toLowerCase().startsWith("telephone")) {
            return InputType.TEL;
        }

        if (f.getType().equals(Double.class) || f.getType().equals(Float.class)
                || f.getType().getName().equals("double") || f.getType().getName().equals("float")
                || f.getType().equals(Integer.class) || f.getType().getName().equals("int")
                || f.getType().equals(Byte.class) || f.getType().getName().equals("byte")
                || f.getType().equals(Short.class) || f.getType().getName().equals("short")
                || f.getType().equals(BigDecimal.class)) {
            return InputType.NUMBER;
        }

        if (f.getType().equals(Boolean.class) || f.getType().getName().equals("boolean")) {
            return InputType.CHECKBOX;
        }
        if (f.isAnnotationPresent(Enumerated.class)) {
            return InputType.SELECTENUM;
        }

        return InputType.TEXT;
    }
    
     public static boolean isfieldlibelle(Field f) {
        return (f.isAnnotationPresent(Libelle.class)
                || f.getName().startsWith("libelle")
                || f.getName().equalsIgnoreCase("designation")
                || f.getName().equalsIgnoreCase("nom")
                || f.getName().equalsIgnoreCase("reference"));
        
    }
}
