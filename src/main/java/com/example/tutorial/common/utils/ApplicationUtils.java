package com.example.tutorial.common.utils;

import com.example.tutorial.common.exceptions.ApplicationTechnicalException;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
public class ApplicationUtils {

  /**
   * Copies properties from the source object to the target object.
   *
   * @param source the source object
   * @param target the target object
   * @throws ApplicationTechnicalException if there is an error during property copying
   */
  public void copyProperties(Object source, Object target) {
    try {
      BeanUtils.copyProperties(target, source);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new ApplicationTechnicalException("Error copying properties from source to target", e);
    }
  }

}
