package org.act.launch.application.dto;


import java.math.BigDecimal;
import java.time.LocalDate;

public class LaunchDTO {

    private String description;

    private BigDecimal amount;

    private Long accountId;

    private Long categoryId;
    private BigDecimal value;
    private LocalDate date;
    private String name;
    private String type;

    public LaunchDTO() {
    }

    public LaunchDTO(String description, BigDecimal amount, Long accountId, Long categoryId) {
        this.description = description;
        this.amount = amount;
        this.accountId = accountId;
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

