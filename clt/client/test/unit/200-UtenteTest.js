//Classe Utente ... OK
describe("Utente", function() {
it("constructor test Utente", function() {
    var utente = new PT.MODEL.Utente ("gino@prova.it","online","Gino");
    expect("gino@prova.it").toEqual(utente.getEmail());
    expect("online").toEqual(utente.getStato());
    expect("Gino").toEqual(utente.getNome());
    
    
    var utente2 = new PT.MODEL.Utente();
    expect(typeof utente2.email).toEqual("undefined");
    expect(typeof utente2.stato).toEqual("undefined");
    expect(typeof utente2.nome).toEqual("undefined");
});


it("test getter and setter", function() {

    var utente = new PT.MODEL.Utente ("gino@prova.it","online","Gino");
    utente.setStato("offline");
    expect("offline").toEqual(utente.getStato());
    
    utente.setNome("Ilenia");
    expect("Ilenia").toEqual(utente.getNome());

});
    
    
});