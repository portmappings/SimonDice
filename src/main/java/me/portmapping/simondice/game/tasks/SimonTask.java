package me.portmapping.simondice.game.tasks;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class SimonTask {
    protected String description;
    protected int timeToComplete = 8;




}
