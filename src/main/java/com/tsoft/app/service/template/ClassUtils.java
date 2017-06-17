/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.service.template;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

/**
 *
 * @author tchipi
 */
public class ClassUtils {

    public static boolean isMultipart(Class classcategorie) throws Exception {
        for (Field f : getFields(classcategorie)) {
            if (FieldUtils.getInputType(f).equals(InputType.FILE)) {
                return true;
            }
        }
        return false;
    }

    public static List<Field> getFields(Class classcategorie) throws Exception {
        final List<Field> result = new ArrayList<>();
        ReflectionUtils.doWithFields(classcategorie, (Field field) -> {
            result.add(field);
        }, (Field f) -> !f.isAnnotationPresent(CreatedBy.class) && !f.isAnnotationPresent(CreatedDate.class)
                && !f.isAnnotationPresent(LastModifiedBy.class) && !f.isAnnotationPresent(LastModifiedDate.class)
                && !f.getName().equalsIgnoreCase("serialVersionUID")
                && !f.getName().equalsIgnoreCase("$jacocoData")
                && !f.isAnnotationPresent(OneToMany.class)
        );
        return result;
    }

    public static List<Field> getFieldsToDisplayInTable(Class classcategorie) throws Exception {
        final List<Field> result = new ArrayList<>();
        ReflectionUtils.doWithFields(classcategorie, (Field field) -> {
            result.add(field);
        }, (Field f) -> !f.isAnnotationPresent(Id.class) && !f.isAnnotationPresent(CreatedBy.class) && !f.isAnnotationPresent(CreatedDate.class)
                && !f.isAnnotationPresent(LastModifiedBy.class) && !f.isAnnotationPresent(LastModifiedDate.class)
                && !f.getName().equalsIgnoreCase("serialVersionUID")
                && !f.getName().equalsIgnoreCase("$jacocoData")
                && !f.isAnnotationPresent(OneToMany.class)
                && !f.isAnnotationPresent(Lob.class)
                && !f.getName().toLowerCase().contains("contenttype")
        );
        return result;
    }

    public String[] getfieldsName(Class classcategorie) throws Exception {
        final List<String> fields = new ArrayList<>();
        String[] result = {};
        for (Field f : getFields(classcategorie)) {
            fields.add(f.getName());
        }

        return fields.toArray(result);
    }

    public static Field getFieldlibelle(Class classcategorie) throws Exception {
        final List<Field> result = new ArrayList<>();
        ReflectionUtils.doWithFields(classcategorie, (final Field f) -> {
            result.add(f);
        }, (Field field) -> (FieldUtils.isfieldlibelle(field)));

        return (result.isEmpty() ? getFieldCode(classcategorie) : result.get(0));
    }

    public  static Field getFieldCode(Class classcategorie) throws Exception {
        final List<Field> result = new ArrayList<>();
        ReflectionUtils.doWithFields(classcategorie, (final Field f) -> {
            result.add(f);
        }, (Field field) -> field.isAnnotationPresent(Id.class));
        return (result.isEmpty() ? null : result.get(0));
    }

    public String[] getfieldnameByAnnotation(Class classcategorie, Class<? extends Annotation> a) throws Exception {
        final List<String> fields = new ArrayList<>();
        String[] result = {};
        ReflectionUtils.doWithFields(classcategorie, (final Field f) -> {
            fields.add(f.getName());
        }, (Field field) -> (field.isAnnotationPresent(a)));

        return fields.toArray(result);
    }

    public List<Field> getfieldsManytoOne(Class classcategorie) throws Exception {
        return getFieldsByAnnotation(classcategorie, ManyToOne.class);
    }

    public List<Field> getFieldsOnetoMany(Class classcategorie) throws Exception {
        return getFieldsByAnnotation(classcategorie, OneToMany.class);
    }

    public List<Field> getFieldsByAnnotation(Class classcategorie, Class<? extends Annotation> a) throws Exception {
        final List<Field> result = new ArrayList<>();
        ReflectionUtils.doWithFields(classcategorie, (final Field f) -> {
            result.add(f);
        }, (Field field) -> field.isAnnotationPresent(a));

        return result;
    }

}
