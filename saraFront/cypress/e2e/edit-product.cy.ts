describe('Edit Product Page', () => {
  beforeEach(() => {
    // Stel de basis URL in van de applicatie
    cy.visit('/edit-product/1'); // Ga naar de edit-product pagina met een voorbeeld product ID 1
  });

  it('should display the Edit Product page', () => {
    // Controleer of de titel correct wordt weergegeven
    cy.contains('h4', 'Edit Product').should('be.visible');

    // Controleer of het formulier en enkele invoervelden zichtbaar zijn
    cy.get('form').should('be.visible');
    cy.get('input[name="productName"]').should('be.visible');
    cy.get('textarea[name="description"]').should('be.visible');
    cy.get('input[name="price"]').should('be.visible');

    // Controleer een van de variant velden
    cy.contains('button', 'Add variant').should('be.visible');
  });
});
