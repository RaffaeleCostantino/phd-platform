/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.dottorato.Cycle;

/**
 *
 * @author tommaso
 */
public class CycleException extends Exception {
       public CycleException() {
        super("Errore oggetto cycle");
    }
    public CycleException(String pMessage) {
        super(pMessage);
    }
}
