Cypress.Commands.add('login', () => {
    const iat = Math.round((new Date()).getTime() / 1000);
    const exp = iat + 21600;
    const jwtPayload = {
      sub: "User Details",
      isEmployee: true,
      iss: "Duck Studios",
      exp: exp,
      iat: iat,
      email: "bob@bobsluxeryenterprise.com"
    };
  
    const jtwPayloadEncoded = btoa(JSON.stringify(jwtPayload));
  
    window.localStorage.setItem('token', `eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.${jtwPayloadEncoded}.fakejtwtoken`);
  });
  
  Cypress.Commands.add('registerProductsFixture', () => {
    cy.intercept(
      'http://localhost:8080/api/products',
      {
        statusCode: 200,
        fixture: 'products/get/product-list.json',
      },
    );
  });
  
  Cypress.Commands.add('addProductsToCart', () => {
    cy.visit('products');
  
    cy.get('[data-cy=add-product-to-cart-button]').eq(0).click();
    cy.get('[data-cy=add-product-to-cart-button]').eq(1).click();
    cy.get('[data-cy=add-product-to-cart-button]').eq(2).click();
  });