package com.knobblochsapplication.app.modules.File_system;

import java.util.ArrayList;

public final class Goal {

    private final int id;

    private String name;
    private String description;
    private int rank;
    private long expiresAt;

    private boolean complete=false;

    private Integer parentId=0;
    private ArrayList<Integer> children=new ArrayList<>();


    // @AllArgsConstructor - конструктор для всех полей
    public Goal(
            int id,
            String name,
            String description,
            int rank,
            long expiresAt
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rank = rank;
        this.expiresAt = expiresAt;
    }

    // @Getter - метод для получения значения поля, сами поля private по соображениям принципа инкапсуляции.
    // Т.к. аннотация поставлена над классом, то геттеры будут созданы для всех полей.
    // Если поставить её над полем - только для него, или можно ставить над классом и исключать ненужное путём
    // установки @Getter(AccessLevel.NONE) над каждым из полей, для которых не нужно будет делать геттер-метод.
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public int getRank() {
        return this.rank;
    }

    public long getExpiresAt() {
        return this.expiresAt;
    }

    public boolean isComplete() {
        return this.complete;
    }

    public Integer getParentId() {
        return this.parentId;
    }

    public ArrayList<Integer> getChildren() {
        return this.children;
    }

    // @Setter - аналог @Getter, но делает методы для установки значений в нужные поля.
    // Маркировка с выбором всех полей или их исключением работает так же.
    // Ломбук игнорирует создание сеттера для final полей, т.к. их значения не изменяемы, поэтому тут нет #setId(int)
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public void setChildren(ArrayList<Integer> children) {
        this.children = children;
    }

}
