/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.dottorato.exception;

/**
 *
 * @author Rembor
 * poichè la madonna è bastarda ci sta pure nella gestione account, curriculum,seminario, se non leggete i
 * porco dio di commit poi elisa cazzia a me che prendo iniziative e mananggia la madonna mi sale il fascismo
 * like a gesù cristo di muhamed
 */
public class NameException extends Exception{
    
     public NameException () {
        super("Il porco dio di nome  e' sbagliato! ");
    }
   
     public NameException(String pMessage) {
        super(pMessage);
    }
}