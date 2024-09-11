package me.portmapping.simonDice.builders;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class SimonTask {
    protected String description;
    protected int timeToComplete = 60;




}
