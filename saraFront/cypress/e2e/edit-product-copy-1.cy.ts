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
    cy.get('input[name="productName"]').clear().type('Updated Test Product', { timeout: 10000 });
    cy.get('textarea[name="description"]').clear().type('This is an updated test product', { timeout: 10000 });
    cy.get('input[name="price"]').clear().type('199.99', { timeout: 10000 });
    cy.get('input[name="imgURL"]').clear().type('http://example.com/updated_image.png', { timeout: 10000 });
    cy.get('input[name="groupset"]').clear().type('Updated Groupset', { timeout: 10000 });
    cy.get('input[name="material"]').clear().type('Updated Carbon', { timeout: 10000 });

    // Voeg een variant toe via de modal
    cy.get('button[data-bs-target="#exampleModal"]').click();
    cy.get('input[name="size"]').clear().type('36', { timeout: 10000 });
    cy.get('input[name="color"]').clear().type('Red', { timeout: 10000 });
    cy.get('button').contains('Modal Submit').click({ force: true });

    // Controleer of de variant succesvol is toegevoegd
    cy.get('#exampleModal').then(($modal) => {
      console.log('Modal after submission:', $modal);
      $modal.remove();
  });

  cy.on('window:alert', (str) => {
    if (str === 'Variant Added!') {
      expect(str).to.equal('Variant Added!');
    }
  });
    // Verzend het formulier
    cy.get('button').contains('Submit').click({ force: true });

    // Controleer of het product succesvol is bijgewerkt
    cy.on('window:alert', (str) => {
      if (str === 'Product Updated!') {
        expect(str).to.equal('Product Updated!');
      }
    });
    
  });
});