describe("Updater", function() {
    var orig_PT = PT;
    var view;

    beforeEach(function() {
        PT = Object.clone(orig_PT);
        jasmine.getFixtures().fixturesPath = '/test/public_html/html';
        jasmine.getFixtures().load('MyTalk-fixture.html');
        PT.VIEW.getInstanceSubjectView = function() {
            view = jasmine.createSpyObj("view", ["fireEvent"]);
            return view;
        };
    });
    afterEach(function() {
        PT = orig_PT;
    });
    it("aggiuntoAmico", function() {
        PT.VIEW.getInstanceUpdater().aggiuntoAmico({
            getNome: function() {
                return 'nome';
            },
            getEmail: function() {
                return 'e@ma.il';
            },
            getStato: function() {
                return 'Online';
            }
        });
        expect($('#amici')).toContain("a[href$='#e@ma.il']");
    });

    it("aggingiPendente", function() {
        PT.VIEW.getInstanceUpdater().aggiungiPendente({
            getNome: function() {
                return 'nome';
            },
            getEmail: function() {
                return 'e@ma.il';
            }
        });
        expect($('#pend')).toContain("a[href$='#e@ma.il']");
        expect($('#tabamici span')).toBeVisible();
        expect($("#pend span")).toHaveClass("label-info");
    });

    it("rimuoviAmico", function() {
        $('#Online').append('<li><span class="checkbox hide"><input type="checkbox" name="selettore" value="e@ma.il"></span><span class="label label-success"><i class="icon-ok-circle icon-white"></i></span><a href="#e@ma.il" class="amico Online" data-original-title title>nome</a></li>');
        $("#elimina ul").append('<li id="nome_elimina"><label class="checkbox inline"><input type="checkbox" name="elimina" value="e@ma.il">nome</input></label></li>');
        PT.VIEW.getInstanceUpdater().rimuoviAmico({
            getNome: function() {
                return 'nome';
            },
            getEmail: function() {
                return 'e@ma.il';
            },
            getStato: function() {
                return 'Online';
            }
        });
        expect($('#Online')).not.toContainHtml('<li></li>');
        expect($("#elimina ul")).toBeEmpty();
    });

    it("cambioStato", function() {
        $('#Online').append('<li><span class="checkbox hide"><input type="checkbox" name="selettore" value="e@ma.il"></span><span class="label label-success"><i class="icon-ok-circle icon-white"></i></span><a href="#e@ma.il" class="amico Online" data-original-title title>nome</a></li>');
        PT.VIEW.getInstanceUpdater().cambioStato({
            getEmail: function() {
                return 'e@ma.il';
            },
            getStato: function() {
                return 'Offline';
            }
        });
        expect($("#Online")).not.toContainHtml('<li></li>');
        expect($("#Offline")).toContain("a[href$='#e@ma.il']");
        expect($("#Offline  a[href='#e@ma.il']")).toHaveClass("Offline");
        expect($("#Offline i")).toHaveClass('icon-ban-circle');

    });
    it("cambioStato1", function() {
        $('#Online').append('<li><span class="checkbox hide"><input type="checkbox" name="selettore" value="e@ma.il"></span><span class="label label-success"><i class="icon-ok-circle icon-white"></i></span><a href="#e@ma.il" class="amico Online" data-original-title title>nome</a></li>');
        $("#um2").prop("checked", true);
        PT.VIEW.getInstanceUpdater().cambioStato({
            getEmail: function() {
                return 'e@ma.il';
            },
            getStato: function() {
                return 'Occupato';
            }
        });
        expect($("#Online")).not.toContainHtml('<li></li>');
        expect($("#Occupato")).toContain("a[href$='#e@ma.il']");
        expect($("#Occupato  a[href='#e@ma.il']")).toHaveClass("Occupato");
        expect($("#Occupato span")).toHaveClass('label-warning');
    });


    it("cambioUsername", function() {
        $('#amici').find('#Online').append('<li><span class="checkbox hide"><input type="checkbox" name="selettore" value="e@ma.il"></span><span class="label label-success"><i class="icon-ok-circle icon-white"></i></span><a href="#e@ma.il" class="amico Online" data-original-title title>nome</a></li>');
        PT.VIEW.getInstanceUpdater().cambioUsername({
            getNome: function() {
                return 'nuovonome';
            },
            getEmail: function() {
                return 'e@ma.il';
            }
        });
        expect($('#amici ul>li>a[href="#e@ma.il"]')).toContainText("nuovonome");
    });

    it("rimuoviPendente", function() {
        $('#pend').find('ul').append('<li><a href="#e@ma.il" class="pendente" data-original-title title>nome</a></li>');
        PT.VIEW.getInstanceUpdater().rimuoviPendente({
            getEmail: function() {
                return 'e@ma.il';
            }
        });

        expect($('#pend ul')).toBeEmpty();
        expect($('#pend p')).toBeVisible();
    });
    it("trattaRichiestaPendenteAccetta", function() {
        PT.VIEW.getInstanceUpdater().aggiungiPendente({
            getNome: function() {
                return "nome";
            },
            getEmail: function() {
                return 'e@ma.il';
            }
        });
        $("#pend a").clickover("toggle");
        var chi = $("#pend ").find(".popover-content").find("span")[0];
        PT.VIEW.getInstanceUpdater().trattaRichiestaPendente(chi, 1);
        expect($("#pend ul")).toBeEmpty();
        expect($("#pend p")).toBeVisible();
        expect(view.fireEvent).toHaveBeenCalled();

    });
    it("trattaRichiestaPendenteRifiuta", function() {
        PT.VIEW.getInstanceUpdater().aggiungiPendente({
            getNome: function() {
                return "nome";
            },
            getEmail: function() {
                return 'e@ma.il';
            }
        });
        $("#pend a").clickover("toggle");
        var chi = $("#pend ").find(".popover-content").find("span")[1];
        PT.VIEW.getInstanceUpdater().trattaRichiestaPendente(chi, 1);
        expect($("#pend ul")).toBeEmpty();
        expect($("#pend p")).toBeVisible();
        expect(view.fireEvent).toHaveBeenCalled();

    });
    it("notificaEsito", function() {
        window.bootbox = jasmine.createSpyObj("bootbox", ["alert"]);
        PT.VIEW.getInstanceUpdater().notificaEsito("titolo", "contenuto");
        expect(window.bootbox.alert).toHaveBeenCalled();
    });
    it("richiestaChiamata", function() {
        window.bootbox = jasmine.createSpyObj("bootbox", ["confirm", "callback"]);
        PT.MODEL.getInstanceRegistro().getListaUtenti = function() {
            return {email: "e@ma.il", nome: "nome"};
        };
        PT.VIEW.getInstanceUpdater().richiestaChiamata({partecipanti: new Array("e@ma.il", "e2@ma.il"), callback: window.bootbox.callback, tipo: "tipo"});
        expect(window.bootbox.confirm).toHaveBeenCalled();
    });
    it("visualizzaEsitoPieno", function() {
        PT.VIEW.getInstanceUpdater().visualizzaRicerca({email: "nome"});
        expect($("#esiti")).toContain("a[href$='#email']");
        expect($("#cerca p")).toBeHidden();
        expect($("#esiti span")).toHaveClass('label-info');
    });
    it("visualizzaEsitoVuoto", function() {
        PT.VIEW.getInstanceUpdater().visualizzaRicerca({});
        expect($("#cerca p")).toBeVisible();

    });
    it("visualizzaMsgSegreteria", function() {
        PT.VIEW.getInstanceUpdater().visualizzaMessaggioSegreteria("io", "testo");
        expect($("#msg")).toBeVisible();
        expect($("#msg h3")).toContainText("Nuovo messaggio di segreteria");
        expect($("#msg p")[0]).toContainText("Mittente: io");
        expect($("#msg p")[1]).toContainText("testo");
        expect($("#msgfooter")).toContainHtml('<button type="submit" class="btn btn-primary" data-dismiss="modal">OK</button>');
    });
    it("scriviMsgSegreteria", function() {
        PT.VIEW.getInstanceUpdater().scriviMessaggioSegreteria("tu");
        $("#msgbody textarea").text("Testo");
        expect($("#msg")).toBeVisible();
        expect($("#msg h3")).toContainText("Scrivi messaggio di segreteria");
        expect($("#msg p")[0]).toContainText("Destinatario: tu");
        expect($("#msgfooter")).toContainHtml('<button class="btn btn-primary">Invia</button>');
        $("#msgfooter .btn-primary").click();
        expect(view.fireEvent).toHaveBeenCalled();
    });

    it("mostraIP", function() {
        PT.VIEW.getInstanceUpdater().settaIp("1.1.1.1");
        expect($("#utenteIP>p")).toContainText("Il tuo IP: 1.1.1.1");
    });

});
