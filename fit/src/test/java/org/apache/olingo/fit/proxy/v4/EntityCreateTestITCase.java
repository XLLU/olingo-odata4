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
package org.apache.olingo.fit.proxy.v4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.TimeZone;

import org.apache.commons.lang3.RandomUtils;
import org.apache.olingo.client.api.v4.EdmEnabledODataClient;
import org.apache.olingo.ext.proxy.EntityContainerFactory;
//CHECKSTYLE:OFF (Maven checkstyle)
import org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.InMemoryEntities;
import org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.types.AccessLevel;
import org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.types.Address;
import org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.types.Color;
import org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.types.Customer;
import org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.types.Employee;
import org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.types.Order;
import org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.types.OrderCollection;
import org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.types.OrderDetail;
import org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.types.OrderDetailKey;
import org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.types.PaymentInstrument;
import org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.types.PaymentInstrumentCollection;
import org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.types.Product;
import org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.types.ProductDetail;
import org.apache.olingo.fit.proxy.v4.staticservice.microsoft.test.odata.services.odatawcfservice.types.ProductDetailCollection;
//CHECKSTYLE:ON (Maven checkstyle)
import org.junit.Test;

/**
 * This is the unit test class to check entity create operations.
 */
public class EntityCreateTestITCase extends AbstractTestITCase {

  protected EntityContainerFactory<EdmEnabledODataClient> getContainerFactory() {
    return containerFactory;
  }

  protected InMemoryEntities getContainer() {
    return container;
  }

  @Test
  public void createAndDelete() {
    createAndDeleteOrder(getContainer(), getContainerFactory());
  }

  @Test
  public void createEmployee() {
    final Integer id = 101;

    final Employee employee = getContainer().getPeople().newEmployee();
    employee.setPersonID(id);
    employee.setFirstName("Fabio");
    employee.setLastName("Martelli");
    employee.setEmails(Collections.<String> singleton("fabio.martelli@tirasa.net"));
    final Calendar date = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    date.clear();
    date.set(2011, 3, 4, 9, 0, 0);
    employee.setDateHired(date);
    final Address homeAddress = employee.factory().newHomeAddress();
    homeAddress.setCity("Pescara");
    homeAddress.setPostalCode("65100");
    homeAddress.setStreet("viale Gabriele D'Annunzio 256");
    employee.setHomeAddress(homeAddress);
    employee.setNumbers(Arrays.asList(new String[] { "3204725072", "08569930" }));

    getContainer().flush();

    Employee actual = getContainer().getPeople().get(id, Employee.class);
    assertNotNull(actual);
    assertEquals(id, actual.getPersonID());
    assertEquals(homeAddress.getCity(), actual.getHomeAddress().getCity());

    getContainerFactory().getContext().detachAll();
    actual = getContainer().getPeople().get(id, Employee.class);
    assertNotNull(actual);
    assertEquals(id, actual.getPersonID());
    assertEquals(homeAddress.getCity(), actual.getHomeAddress().getCity());

    getContainer().getPeople().delete(actual.getPersonID());
    getContainer().flush();

    actual = getContainer().getPeople().get(id, Employee.class);
    assertNull(actual);

    getContainerFactory().getContext().detachAll();
    actual = getContainer().getPeople().get(id, Employee.class);
    assertNull(actual);
  }

  @Test
  public void createWithNavigation() {
    final Integer id = 101;

    final Customer customer = getContainer().getCustomers().newCustomer();
    customer.setPersonID(id);
    customer.setPersonID(id);
    customer.setFirstName("Fabio");
    customer.setLastName("Martelli");
    customer.setCity("Pescara");
    customer.setEmails(Collections.<String> singleton("fabio.martelli@tirasa.net"));
    Address homeAddress = customer.factory().newHomeAddress();
    homeAddress.setCity("Pescara");
    homeAddress.setPostalCode("65100");
    homeAddress.setStreet("viale Gabriele D'Annunzio 256");
    customer.setHomeAddress(homeAddress);
    customer.setNumbers(Arrays.asList(new String[] { "3204725072", "08569930" }));

    final OrderCollection orders = getContainer().getOrders().newOrderCollection();
    orders.add(getContainer().getOrders().get(8));
    customer.setOrders(orders);

    getContainer().flush();

    Customer actual = readCustomer(getContainer(), id);
    assertEquals(homeAddress.getCity(), actual.getHomeAddress().getCity());
    assertEquals(1, actual.getOrders().size());
    assertEquals(8, actual.getOrders().iterator().next().getOrderID(), 0);

    getContainer().getCustomers().delete(actual.getPersonID());
    getContainer().flush();

    actual = getContainer().getCustomers().get(id);
    assertNull(actual);
  }

  @Test
  public void createWithBackNavigation() {
    final Integer id = 102;

    // -------------------------------
    // Create a new order
    // -------------------------------
    Order order = getContainer().getOrders().newOrder();
    order.setOrderID(id);

    final Calendar orderDate = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    orderDate.clear();
    orderDate.set(2011, 3, 4, 16, 3, 57);
    order.setOrderDate(orderDate);

    order.setShelfLife(BigDecimal.TEN);
    order.setOrderShelfLifes(Arrays.asList(new BigDecimal[] { BigDecimal.TEN.negate(), BigDecimal.TEN }));
    // -------------------------------

    // -------------------------------
    // Create a new customer
    // -------------------------------
    final Customer customer = getContainer().getCustomers().newCustomer();
    customer.setPersonID(id);
    customer.setPersonID(id);
    customer.setFirstName("Fabio");
    customer.setLastName("Martelli");
    customer.setCity("Pescara");
    customer.setEmails(Collections.<String> singleton("fabio.martelli@tirasa.net"));
    final Address homeAddress = customer.factory().newHomeAddress();
    homeAddress.setCity("Pescara");
    homeAddress.setPostalCode("65100");
    homeAddress.setStreet("viale Gabriele D'Annunzio 256");
    customer.setHomeAddress(homeAddress);
    customer.setNumbers(Arrays.asList(new String[] { "3204725072", "08569930" }));

    final OrderCollection orders = getContainer().getOrders().newOrderCollection();
    orders.add(order);
    customer.setOrders(orders);
    // -------------------------------

    // -------------------------------
    // Link customer to order
    // -------------------------------
    order.setCustomerForOrder(customer);
    // -------------------------------

    getContainer().flush();

    assertEquals(id, order.getOrderID());
    assertEquals(id, customer.getPersonID());

    Customer actual = readCustomer(getContainer(), id);
    assertEquals(homeAddress.getCity(), actual.getHomeAddress().getCity());
    assertEquals(1, actual.getOrders().size());
    assertEquals(id, actual.getOrders().iterator().next().getOrderID());

    order = getContainer().getOrders().get(id);
    assertNotNull(order);
    assertEquals(id, order.getCustomerForOrder().getPersonID());

    getContainer().getOrders().delete(actual.getOrders());
    getContainer().flush();

    order = getContainer().getOrders().get(id);
    assertNull(order);

    actual = readCustomer(getContainer(), id);
    assertTrue(actual.getOrders().isEmpty());

    getContainer().getCustomers().delete(actual.getPersonID());
    getContainer().flush();

    actual = getContainer().getCustomers().get(id);
    assertNull(actual);
  }

  @Test
  public void multiKey() {
    OrderDetail details = getContainer().getOrderDetails().newOrderDetail();
    details.setOrderID(8);
    details.setProductID(1);
    details.setQuantity(100);
    details.setUnitPrice(5f);

    getContainer().flush();

    OrderDetailKey key = new OrderDetailKey();
    key.setOrderID(8);
    key.setProductID(1);

    details = getContainer().getOrderDetails().get(key);
    assertNotNull(details);
    assertEquals(Integer.valueOf(100), details.getQuantity());
    assertEquals(8, details.getOrderID(), 0);
    assertEquals(1, details.getProductID(), 0);
    assertEquals(5f, details.getUnitPrice(), 0);

    getContainer().getOrderDetails().delete(key);
    getContainer().flush();

    assertNull(getContainer().getOrderDetails().get(key));
  }

  @Test
  public void deepInsert() {
    Product product = getContainer().getProducts().newProduct();
    product.setProductID(12);
    product.setName("Latte");
    product.setQuantityPerUnit("100g Bag");
    product.setUnitPrice(3.24f);
    product.setQuantityInStock(100);
    product.setDiscontinued(false);
    product.setUserAccess(AccessLevel.Execute);
    product.setSkinColor(Color.Blue);
    product.setCoverColors(Arrays.asList(new Color[] { Color.Red, Color.Green }));

    final ProductDetail detail = getContainer().getProductDetails().newProductDetail();
    detail.setProductID(product.getProductID());
    detail.setProductDetailID(12);
    detail.setProductName("LatteHQ");
    detail.setDescription("High-Quality Milk");

    final ProductDetailCollection detailCollection = getContainer().getProductDetails().newProductDetailCollection();
    detailCollection.add(detail);

    product.setDetails(detailCollection);

    getContainer().flush();

    product = getContainer().getProducts().get(12);
    assertEquals("Latte", product.getName());
    assertEquals(12, product.getDetails().iterator().next().getProductDetailID(), 0);
  }

  @Test
  public void contained() {
    PaymentInstrumentCollection instruments = getContainer().getAccounts().get(101).getMyPaymentInstruments().getAll();
    final int sizeBefore = instruments.size();

    final PaymentInstrument instrument = getContainer().getAccounts().get(101).
        getMyPaymentInstruments().newPaymentInstrument();

    final int id = RandomUtils.nextInt(101999, 105000);
    instrument.setPaymentInstrumentID(id);
    instrument.setFriendlyName("New one");
    instrument.setCreatedDate(Calendar.getInstance());

    getContainer().flush();

    instruments = getContainer().getAccounts().get(101).getMyPaymentInstruments().getAll();
    final int sizeAfter = instruments.size();
    assertEquals(sizeBefore + 1, sizeAfter);

    getContainer().getAccounts().get(101).getMyPaymentInstruments().delete(id);

    getContainer().flush();

    instruments = getContainer().getAccounts().get(101).getMyPaymentInstruments().getAll();
    final int sizeEnd = instruments.size();
    assertEquals(sizeBefore, sizeEnd);
  }
}
