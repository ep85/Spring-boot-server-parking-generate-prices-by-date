package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * RateItem
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-12-27T23:23:02.463Z")

@Entity
@Table(name="rates")
public class RateItem   {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  @JsonProperty("days")
  private String days = null;

  @JsonProperty("times")
  private String times = null;

  @JsonProperty("tz")
  private String tz = null;

  @JsonProperty("price")
  private Long price = null;

  public RateItem days(String days) {
    this.days = days;
    return this;
  }

  /**
   * Get days
   * @return days
  **/
  @ApiModelProperty(example = "mon,tues", required = true, value = "")
  @NotNull


  public String getDays() {
    return days;
  }

  public void setDays(String days) {
    this.days = days;
  }

  public RateItem times(String times) {
    this.times = times;
    return this;
  }

  /**
   * Get times
   * @return times
  **/
  @ApiModelProperty(example = "0900-2100", required = true, value = "")
  @NotNull


  public String getTimes() {
    return times;
  }

  public void setTimes(String times) {
    this.times = times;
  }

  public RateItem tz(String tz) {
    this.tz = tz;
    return this;
  }

  /**
   * Get tz
   * @return tz
  **/
  @ApiModelProperty(example = "America/Chicago", required = true, value = "")
  @NotNull


  public String getTz() {
    return tz;
  }

  public void setTz(String tz) {
    this.tz = tz;
  }

  public RateItem price(Long price) {
    this.price = price;
    return this;
  }

  /**
   * Get price
   * @return price
  **/
  @ApiModelProperty(example = "1500.0", required = true, value = "")
  @NotNull

  @Valid

  public Long getPrice() {
    return price;
  }

  public void setPrice(Long price) {
    this.price = price;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RateItem rateItem = (RateItem) o;
    return Objects.equals(this.days, rateItem.days) &&
        Objects.equals(this.times, rateItem.times) &&
        Objects.equals(this.tz, rateItem.tz) &&
        Objects.equals(this.price, rateItem.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(days, times, tz, price);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RateItem {\n");
    
    sb.append("    days: ").append(toIndentedString(days)).append("\n");
    sb.append("    times: ").append(toIndentedString(times)).append("\n");
    sb.append("    tz: ").append(toIndentedString(tz)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

