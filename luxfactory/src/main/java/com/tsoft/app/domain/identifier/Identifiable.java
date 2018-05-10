/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.domain.identifier;

import java.io.Serializable;

/**
 *
 * @author tchipnangngansopa
 * @param <T>
 */
public interface Identifiable <T extends Serializable> {
    T getId();
}
