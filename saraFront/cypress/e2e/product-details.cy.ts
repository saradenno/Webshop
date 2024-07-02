describe('product-details', () => {
  it('should visit product detail', function() {
    cy.visit('http://localhost:4200/products/1')
  });
})
