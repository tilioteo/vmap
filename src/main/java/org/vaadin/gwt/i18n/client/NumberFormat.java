/*
 * Copyright 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.vaadin.gwt.i18n.client;

import org.vaadin.gwt.i18n.client.impl.NumberFormatImplInteger;
import org.vaadin.gwt.i18n.client.impl.NumberFormatImplNumber;
import org.vaadin.gwt.i18n.client.impl.NumberFormatImplPercent;

import com.google.gwt.core.client.GWT;

/**
 * Abstract base class of all number formats.
 */
public abstract class NumberFormat extends Format {

  public static NumberFormat getInstance() {
    return getNumberInstance();
  }
 
  public static NumberFormat getIntegerInstance() {
    return GWT.create(NumberFormatImplInteger.class);
  }
 
  public static NumberFormat getNumberInstance() {
    return GWT.create(NumberFormatImplNumber.class);
  }
 
  public static NumberFormat getPercentInstance() {
    return GWT.create(NumberFormatImplPercent.class);
  }
 
  protected int maxFractionDigits = 99;

  protected int maxIntegerDigits = 99;

  protected int minFractionDigits = 0;

  protected int minIntegerDigits = 1;
 
  protected boolean groupingUsed;
 
  protected boolean integerOnly;
 
  protected NumberFormat() {
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof NumberFormat)) {
      return false;
    }
    NumberFormat nf = (NumberFormat) other;
    return maxFractionDigits == nf.maxFractionDigits
        && minFractionDigits == nf.minFractionDigits
        && maxIntegerDigits == nf.maxIntegerDigits
        && minIntegerDigits == nf.minIntegerDigits
        && groupingUsed == nf.groupingUsed
        && integerOnly == nf.integerOnly;
  }
 
  public String format(double number) {
    return format(number, new StringBuffer()).toString();
  }

  public String format(long number) {
    return format(number, new StringBuffer()).toString();
  }
 
  public int getMaximumFractionDigits() {
    return maxFractionDigits;
  }
 
  public int getMaxIntegerDigits() {
    return maxIntegerDigits;
  }
 
  public int getMinimumFractionDigits() {
    return minFractionDigits;
  }
 
  public int getMinimumIntegerDigits() {
    return minIntegerDigits;
  }
 
  @Override
  public int hashCode() {
    return maxFractionDigits * 2
        + minFractionDigits * 3
        + maxIntegerDigits * 5
        + minIntegerDigits * 7
        + (groupingUsed ? 11 : 0)
        + (integerOnly ? 13 : 0);
  }
 
  public boolean isGroupingUsed() {
    return groupingUsed;
  }
 
  public boolean isParseIntegerOnly() {
    return integerOnly;
  }
 
  public void setGroupingUsed(boolean groupingUsed) {
    this.groupingUsed = groupingUsed;
  }
 
  public void setMaximumFractionDigits(int digits) {
    maxFractionDigits = digits;
    if (digits < minFractionDigits) {
      minFractionDigits = digits;
    }
  }
 
  public void setMaximumIntegerDigits(int digits) {
    maxIntegerDigits = digits;
    if (digits < minIntegerDigits) {
      minIntegerDigits = digits;
    }
  }
 
  public void setMinimumFractionDigits(int digits) {
    minFractionDigits = digits;
    if (digits > maxFractionDigits) {
      maxFractionDigits = digits;
    }
  }
 
  public void setMinimumIntegerDigits(int digits) {
    minIntegerDigits = digits;
    if (digits > maxIntegerDigits) {
      maxIntegerDigits = digits;
    }
  }
 
  public void setParseIntegerOnly(boolean integerOnly) {
    this.integerOnly = integerOnly;
  }
 
  // Note API difference, so protected
  protected abstract StringBuffer format(double number, StringBuffer buf);
 
  // Note API difference, so protected
  protected abstract StringBuffer format(long number, StringBuffer buf);

  @Override
  protected StringBuffer format(Object obj, StringBuffer buf) {
    if (!(obj instanceof Number)) {
      throw new IllegalArgumentException("format on non-Number object");
    }
    Number num = (Number) obj;
    // longs can't use double without losing data
    if (num instanceof Long) {
      return format(num.longValue(), buf);
    }
    return format(num.doubleValue(), buf);
  }
}
