/* file:        Notifica.java
 * package:     it.pt.mt.srv.controller.notifiche
 * progetto:    server
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-15  TF  Prima stesura
 */
package it.pt.mt.srv.controller.notifiche;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Rappresenta una notifica generica.
 */
public abstract class Notifica {

    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * Restituisce il tipo della notifica.
     * @return il tipo della notifica
     */
    public abstract String getTipo();

    /**
     * Serializza la notifica e la Restituisce come stringa.
     * @return la stringa che contiene la serializzazione della notifica
     */
    public String serializza() {
        mapper = new ObjectMapper();
        try {
            ObjectNode root = new ObjectNode(JsonNodeFactory.instance);

            root.put("tipo", getTipo());

            JsonNode evento = mapper.valueToTree(this);
            ((ObjectNode) evento).remove("tipo");
            root.put("evento", evento);

            return mapper.writeValueAsString(root);
        } catch (JsonProcessingException ex) {
            return "";
        }
    }
}
