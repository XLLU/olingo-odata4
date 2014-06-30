/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.olingo.fit.proxy.v4.demo.odatademo.types;

import org.apache.olingo.client.api.edm.ConcurrencyMode;
import org.apache.olingo.commons.api.edm.constants.EdmContentKind;
import org.apache.olingo.ext.proxy.api.Annotatable;
import org.apache.olingo.ext.proxy.api.annotations.Key;

@org.apache.olingo.ext.proxy.api.annotations.Namespace("ODataDemo")
@org.apache.olingo.ext.proxy.api.annotations.EntityType(name = "Employee",
    openType = false,
    hasStream = false,
    isAbstract = false,
    baseType = "ODataDemo.Person")
public interface Employee
    extends Annotatable, org.apache.olingo.fit.proxy.v4.demo.odatademo.types.Person {

  @Override
  @Key
  @org.apache.olingo.ext.proxy.api.annotations.Property(name = "ID",
      type = "Edm.Int32",
      nullable = false,
      defaultValue = "",
      maxLenght = Integer.MAX_VALUE,
      fixedLenght = false,
      precision = 0,
      scale = 0,
      unicode = true,
      collation = "",
      srid = "",
      concurrencyMode = ConcurrencyMode.None,
      fcSourcePath = "",
      fcTargetPath = "",
      fcContentKind = EdmContentKind.text,
      fcNSPrefix = "",
      fcNSURI = "",
      fcKeepInContent = false)
  java.lang.Integer getID();

  @Override
  void setID(java.lang.Integer _iD);

  @Override
  @org.apache.olingo.ext.proxy.api.annotations.Property(name = "Name",
      type = "Edm.String",
      nullable = true,
      defaultValue = "",
      maxLenght = Integer.MAX_VALUE,
      fixedLenght = false,
      precision = 0,
      scale = 0,
      unicode = true,
      collation = "",
      srid = "",
      concurrencyMode = ConcurrencyMode.None,
      fcSourcePath = "",
      fcTargetPath = "",
      fcContentKind = EdmContentKind.text,
      fcNSPrefix = "",
      fcNSURI = "",
      fcKeepInContent = false)
  java.lang.String getName();

  @Override
  void setName(java.lang.String _name);

  @org.apache.olingo.ext.proxy.api.annotations.Property(name = "EmployeeID",
      type = "Edm.Int64",
      nullable = false,
      defaultValue = "",
      maxLenght = Integer.MAX_VALUE,
      fixedLenght = false,
      precision = 0,
      scale = 0,
      unicode = true,
      collation = "",
      srid = "",
      concurrencyMode = ConcurrencyMode.None,
      fcSourcePath = "",
      fcTargetPath = "",
      fcContentKind = EdmContentKind.text,
      fcNSPrefix = "",
      fcNSURI = "",
      fcKeepInContent = false)
  java.lang.Long getEmployeeID();

  void setEmployeeID(java.lang.Long _employeeID);

  @org.apache.olingo.ext.proxy.api.annotations.Property(name = "HireDate",
      type = "Edm.DateTimeOffset",
      nullable = false,
      defaultValue = "",
      maxLenght = Integer.MAX_VALUE,
      fixedLenght = false,
      precision = 0,
      scale = 0,
      unicode = true,
      collation = "",
      srid = "",
      concurrencyMode = ConcurrencyMode.None,
      fcSourcePath = "",
      fcTargetPath = "",
      fcContentKind = EdmContentKind.text,
      fcNSPrefix = "",
      fcNSURI = "",
      fcKeepInContent = false)
  java.util.Calendar getHireDate();

  void setHireDate(java.util.Calendar _hireDate);

  @org.apache.olingo.ext.proxy.api.annotations.Property(name = "Salary",
      type = "Edm.Single",
      nullable = false,
      defaultValue = "",
      maxLenght = Integer.MAX_VALUE,
      fixedLenght = false,
      precision = 0,
      scale = 0,
      unicode = true,
      collation = "",
      srid = "",
      concurrencyMode = ConcurrencyMode.None,
      fcSourcePath = "",
      fcTargetPath = "",
      fcContentKind = EdmContentKind.text,
      fcNSPrefix = "",
      fcNSURI = "",
      fcKeepInContent = false)
  java.lang.Float getSalary();

  void setSalary(java.lang.Float _salary);

  @Override
  @org.apache.olingo.ext.proxy.api.annotations.NavigationProperty(name = "PersonDetail",
      type = "ODataDemo.PersonDetail",
      targetSchema = "ODataDemo",
      targetContainer = "DemoService",
      targetEntitySet = "PersonDetails",
      containsTarget = false)
  org.apache.olingo.fit.proxy.v4.demo.odatademo.types.PersonDetail getPersonDetail();

  @Override
  void setPersonDetail(org.apache.olingo.fit.proxy.v4.demo.odatademo.types.PersonDetail _personDetail);

  @Override
  ComplexFactory factory();

  interface ComplexFactory extends org.apache.olingo.fit.proxy.v4.demo.odatademo.types.Person.ComplexFactory {}

  @Override
  Annotations annotations();

  interface Annotations extends org.apache.olingo.fit.proxy.v4.demo.odatademo.types.Person.Annotations {

    @Override
    @org.apache.olingo.ext.proxy.api.annotations.AnnotationsForProperty(name = "ID",
        type = "Edm.Int32")
    Annotatable getIDAnnotations();

    @Override
    @org.apache.olingo.ext.proxy.api.annotations.AnnotationsForProperty(name = "Name",
        type = "Edm.String")
    Annotatable getNameAnnotations();

    @org.apache.olingo.ext.proxy.api.annotations.AnnotationsForProperty(name = "EmployeeID",
        type = "Edm.Int64")
    Annotatable getEmployeeIDAnnotations();

    @org.apache.olingo.ext.proxy.api.annotations.AnnotationsForProperty(name = "HireDate",
        type = "Edm.DateTimeOffset")
    Annotatable getHireDateAnnotations();

    @org.apache.olingo.ext.proxy.api.annotations.AnnotationsForProperty(name = "Salary",
        type = "Edm.Single")
    Annotatable getSalaryAnnotations();

    @Override
    @org.apache.olingo.ext.proxy.api.annotations.AnnotationsForNavigationProperty(name = "PersonDetail",
        type = "ODataDemo.PersonDetail")
    Annotatable getPersonDetailAnnotations();
  }

}
