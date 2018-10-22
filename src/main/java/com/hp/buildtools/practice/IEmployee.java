package com.hp.buildtools.practice;

import java.io.Serializable;

public interface IEmployee extends Serializable, Cloneable {

    IEmployee clone();
    IEmployee makeCopy();

    void display();

}
