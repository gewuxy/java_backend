package cn.medcn.common.supports;

import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.ReflectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 参数Bean验证器
 * Created by lixuan on 2017/7/20.
 */
public class BeanValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    @Override
    public void validate(Object object, Errors errors) {
        if (errors != null && errors.hasErrors()) {
            return;
        }
        Field[] fields = object.getClass().getDeclaredFields();
        if (!CheckUtils.isEmpty(fields)) {
            for (Field field : fields) {
                if (field.getType().isPrimitive()) {
                    if (!validatePrimitive(object, field, errors))
                        break;
                } else if (field.getType().equals(String.class)) {
                    if (!validateString(object, field, errors))
                        break;
                } else if (Number.class.isAssignableFrom(field.getType())) {
                    if (validateNumber(object, field, errors))
                        break;
                } else if (Date.class.isAssignableFrom(field.getType())) {
                    if (validateDate(object, field, errors))
                        break;
                }
            }
        }
    }

    /**
     * 验证日期格式类型
     *
     * @param object
     * @param field
     * @param errors
     */
    private boolean validateDate(Object object, Field field, Errors errors) {
        Validate validate = field.getAnnotation(Validate.class);
        if (validate != null) {
            Date value = (Date) ReflectUtils.getFieldValue(object, field);
            String fieldNameStr = validate.filedName();
            String fieldName = field.getName();
            if (validate.notNull() && value == null) {
                errors.rejectValue(fieldName, null, fieldNameStr + "不能为空");
                return false;
            } else if (value != null) {
                Date minDate = strToDate(validate.min());
                if (minDate != null && minDate.after(value)) {
                    errors.rejectValue(fieldName, null, fieldNameStr + "不能小于" + validate.min());
                    return false;
                }
                Date maxDate = strToDate(validate.max());
                if (maxDate != null && maxDate.before(value)) {
                    errors.rejectValue(fieldName, null, fieldNameStr + "不能大于" + validate.max());
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 验证字符串类型
     *
     * @param object
     * @param field
     * @param errors
     */
    private boolean validateString(Object object, Field field, Errors errors) {
        Validate validate = field.getAnnotation(Validate.class);
        if (validate != null) {
            String value = (String) ReflectUtils.getFieldValue(object, field);
            String fieldNameStr = validate.filedName();
            String fieldName = field.getName();
            if (validate.notNull() && value == null) {
                errors.rejectValue(fieldName, null, fieldNameStr + "不能为空");
                return false;
            }
            if (validate.notBlank() && CheckUtils.isEmpty(value)) {
                errors.rejectValue(fieldName, null, fieldNameStr + "不能为空或者空字符串");
                return false;
            }
            if (!CheckUtils.isEmpty(value)) {
                if (validate.maxLength() < value.length() || validate.minLength() > value.length()) {
                    errors.rejectValue(fieldName, null, String.format("%s的长度必须在%d到%d之间", fieldNameStr, validate.minLength(), validate.maxLength()));
                    return false;
                }
                if (!CheckUtils.isEmpty(validate.pattern())) {
                    Matcher matcher = Pattern.compile(validate.pattern()).matcher(value);
                    if (!matcher.matches()) {
                        errors.rejectValue(fieldName, null, String.format("%s格式不符合规范"));
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /***
     * 验证数值类型
     * @param object
     * @param field
     * @param errors
     */
    private boolean validateNumber(Object object, Field field, Errors errors) {
        Validate validate = field.getAnnotation(Validate.class);
        if (validate != null) {
            //数据类型 检测notNull、min和max
            Number value = (Number) ReflectUtils.getFieldValue(object, field);
            String fieldName = validate.filedName();
            if (validate.notNull() && value == null) {
                errors.rejectValue(field.getName(), null, fieldName + "不能为空");
                return false;
            }
            if (value != null) {
                if (Integer.parseInt(validate.max()) < value.longValue()) {
                    errors.rejectValue(field.getName(), null, fieldName + "不能大于" + validate.max());
                    return false;
                }
                if (Long.parseLong(validate.min()) > value.intValue()) {
                    errors.rejectValue(field.getName(), null, fieldName + "不能小于" + validate.min());
                    return false;
                }
            }
        }
        return true;
    }

    /***
     * 验证基本类型中的数值类型
     * @param object
     * @param field
     * @param errors
     */
    private boolean validatePrimitive(Object object, Field field, Errors errors) {
        Validate validate = field.getAnnotation(Validate.class);
        if (validate != null && isPrimitiveNumber(field)) {
            //基本类型 只比较min和max
            Number value = (Number) ReflectUtils.getFieldValue(object, field);
            String fieldName = validate.filedName();
            if (Long.parseLong(validate.max()) < value.longValue()) {
                errors.rejectValue(field.getName(), null, fieldName + "不能大于" + validate.max());
                return false;
            }
            if (Integer.parseInt(validate.min()) > value.intValue()) {
                errors.rejectValue(field.getName(), null, fieldName + "不能小于" + validate.min());
                return false;
            }
        }
        return true;
    }


    private boolean isPrimitiveNumber(Field field) {
        Class clazz = field.getType();
        if (clazz.equals(int.class) ||
                clazz.equals(long.class) ||
                clazz.equals(float.class) ||
                clazz.equals(double.class) ||
                clazz.equals(short.class) ||
                clazz.equals(byte.class)) {
            return true;
        }
        return false;
    }


    private Date strToDate(String dateStr) {
        if (CheckUtils.isEmpty(dateStr)) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = dateFormat.parse(dateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


}
