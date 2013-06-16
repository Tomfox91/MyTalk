describe("GestoreAccount", function() {
var view;
var orig_PT=PT;
    beforeEach(function(){
        PT = Object.clone(orig_PT);
        jasmine.getFixtures().fixturesPath = '/test/public_html/html';
        jasmine.getFixtures().load('MyTalk-fixture.html');
        PT.VIEW.getInstanceSubjectView=function(){view=jasmine.createSpyObj("view",["fireEvent"]);return view;};
        });
    afterEach(function() {
        PT = orig_PT;
  });
it("notificaDomanda",function(){
	(new PT.VIEW.GestoreAccount()).notificaDomanda("domanda");
	expect($("#dr p")).toHaveText("domanda");
	expect($("#insem")).toBeHidden();
	expect($("#dr")).toBeVisible();
	expect($("#newpass")).toBeVisible();
	expect($("#conferma")).toBeHidden();
});
it("cancella",function(){
	var gest=new PT.VIEW.GestoreAccount();
	gest.aggiungiRimozione("nome","e@ma.il");
	$("#elimina input").attr("checked","checked");
	gest.cancella();
	expect(view.fireEvent).toHaveBeenCalled();	
});

it("setUsername",function(){
	(new PT.VIEW.GestoreAccount()).setNewUsername("nuovonome");
	expect($("#nome span")).toHaveText("nuovonome");

});
it("login",function(){
(new PT.VIEW.GestoreAccount()).effettuaAccesso();
expect($("#nome")).toBeVisible();
expect($("#log")).toBeHidden();
expect($("#logbtn")).toHaveAttr("disabled","disabled");
expect($("#logout")).toBeVisible();
expect($("#lst")).toBeHidden();
expect($("#reg")).toBeHidden();
expect($("#tab")).toBeVisible();
expect($("#client>li")).toBeVisible();
expect($("#opz")).toHaveAttr("style","");
});
it("statoPersonale",function(){
	(new PT.VIEW.GestoreAccount()).cambioStatoPersonale("Occupato");
	expect($("#onoff>span")).toHaveClass("label-warning");
});
});

