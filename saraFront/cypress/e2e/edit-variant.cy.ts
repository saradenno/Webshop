describe('Login to the app', () => {
  it('should log in and store the JWT token', () => {
    // Intercept all POST requests to inspect them
    cy.intercept('POST', '**').as('allPosts');

    // Go to the login page and log in
    cy.visit('http://localhost:4200/admin/login', { timeout: 10000 });
    cy.get('input[type="text"]').should('be.visible').type('admin@admin.com', { timeout: 10000 });
    cy.get('input[type="password"]').should('be.visible').type('Admin@123', { timeout: 10000 });
    cy.get('button').contains('Login').click();

    // Wait for any POST request and log details
    cy.wait('@allPosts', { timeout: 10000 }).then((interception) => {
      // Log the intercepted request to inspect details
      console.log('Intercepted Request:', interception);
      // Check if the intercepted request is the login request
      if (interception.request.url.includes('/admin/login')) {
        // Extract and log the response
        const response = interception.response;
        console.log('Login Response:', response);

        if (!response) {
          throw new Error('No response object found in the interception');
        }
        if (!response.body) {
          throw new Error('No body found in response');
        }
        const token = response.body.token;
        if (!token) {
          throw new Error('Token not found in response body');
        }
        window.localStorage.setItem('jwtToken', token);
      } else {
        throw new Error('Expected login request not intercepted');
      }
    });
    cy.visit('http://localhost:4200/admin/edit-product/1', { timeout: 10000 });
      // Klik op de "Add Variant" knop
   cy.get('button').contains('Add Variant').click();

   // Selecteer de laatste variantvelden nauwkeuriger
   cy.get('input[placeholder="Color"]').last().should('exist');
   cy.get('input[placeholder="Size"]').last().should('exist');
   cy.get('input[placeholder="Stock"]').last().should('exist');
   cy.get('input[placeholder="Price"]').last().should('exist');
   cy.get('input[placeholder="Image URL"]').last().should('exist');

   // Vul de variant details in
   cy.get('input[placeholder="Color"]').last().clear().type('Red', { timeout: 10000 });
   cy.get('input[placeholder="Size"]').last().clear().type('36', { timeout: 10000 });
   cy.get('input[placeholder="Stock"]').last().clear().type('50', { timeout: 10000 });
   cy.get('input[placeholder="Price"]').last().clear().type('150', { timeout: 10000 });
   cy.get('input[placeholder="Image URL"]').last().clear().type('http://example.com/variant_image.png', { timeout: 10000 });

   // Verzend het formulier
   cy.get('button').contains('Submit').click();
  })
 

});