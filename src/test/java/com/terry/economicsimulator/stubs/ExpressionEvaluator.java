package com.terry.economicsimulator.stubs;

import com.terry.economicsimulator.models.ResourceType;
import com.terry.economicsimulator.models.input.expression.*;
import com.terry.economicsimulator.models.input.filter.Filter;

import java.time.*;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ExpressionEvaluator {
  private ExpressionEvaluator() {
  }

  /**
   * Date Expression
   */
  public static <T extends Filter> boolean evaluateDateExpression(T filter, Function<T, DateExpression> expressionFunc, LocalDate dateToCheck) {
    if (filter == null) {
      return true;
    }

    DateExpression dateExpression = expressionFunc.apply(filter);
    if (dateExpression == null) {
      return true;
    }

    boolean shouldInclude = true;
    if (dateExpression.getEq() != null) {
      shouldInclude = dateToCheck.equals(dateExpression.getEq().atStartOfDay().toLocalDate());
    }
    if (dateExpression.getNe() != null) {
      shouldInclude = !dateToCheck.equals(dateExpression.getNe().atStartOfDay().toLocalDate());
    }
    if (dateExpression.getLt() != null) {
      shouldInclude = shouldInclude && dateToCheck.isBefore(dateExpression.getLt().atStartOfDay().toLocalDate());
    }
    if (dateExpression.getLte() != null) {
      shouldInclude = shouldInclude && !dateToCheck.isAfter(dateExpression.getLte().atStartOfDay().toLocalDate());
    }
    if (dateExpression.getGt() != null) {
      shouldInclude = shouldInclude && dateToCheck.isAfter(dateExpression.getGt().atStartOfDay().toLocalDate());
    }
    if (dateExpression.getGte() != null) {
      shouldInclude = shouldInclude && !dateToCheck.isBefore(dateExpression.getGte().atStartOfDay().toLocalDate());
    }

    return shouldInclude;
  }

  /**
   * Time Expression
   */
  public static <T extends Filter> boolean evaluateTimeExpression(T filter, Function<T, TimeExpression> expressionFunc, OffsetTime timeToCheck) {
    if (filter == null) {
      return true;
    }

    TimeExpression timeExpression = expressionFunc.apply(filter);
    if (timeExpression == null) {
      return true;
    }

    boolean shouldInclude = true;
    if (timeExpression.getEq() != null) {
      shouldInclude = timeToCheck.equals(timeExpression.getEq());
    }
    if (timeExpression.getNe() != null) {
      shouldInclude = !timeToCheck.equals(timeExpression.getNe());
    }
    if (timeExpression.getLt() != null) {
      shouldInclude = shouldInclude && timeToCheck.isBefore(timeExpression.getLt());
    }
    if (timeExpression.getLte() != null) {
      shouldInclude = shouldInclude && !timeToCheck.isAfter(timeExpression.getLte());
    }
    if (timeExpression.getGt() != null) {
      shouldInclude = shouldInclude && timeToCheck.isAfter(timeExpression.getGt());
    }
    if (timeExpression.getGte() != null) {
      shouldInclude = shouldInclude && !timeToCheck.isBefore(timeExpression.getGte());
    }

    return shouldInclude;
  }

  /**
   * LocalTime Expression
   */
  public static <T extends Filter> boolean evaluateLocalTimeExpression(T filter, Function<T, LocalTimeExpression> expressionFunc, LocalTime timeToCheck) {
    if (filter == null) {
      return true;
    }

    LocalTimeExpression timeExpression = expressionFunc.apply(filter);
    if (timeExpression == null) {
      return true;
    }

    boolean shouldInclude = true;
    if (timeExpression.getEq() != null) {
      shouldInclude = timeToCheck.equals(timeExpression.getEq());
    }
    if (timeExpression.getNe() != null) {
      shouldInclude = !timeToCheck.equals(timeExpression.getNe());
    }
    if (timeExpression.getLt() != null) {
      shouldInclude = shouldInclude && timeToCheck.isBefore(timeExpression.getLt());
    }
    if (timeExpression.getLte() != null) {
      shouldInclude = shouldInclude && !timeToCheck.isAfter(timeExpression.getLte());
    }
    if (timeExpression.getGt() != null) {
      shouldInclude = shouldInclude && timeToCheck.isAfter(timeExpression.getGt());
    }
    if (timeExpression.getGte() != null) {
      shouldInclude = shouldInclude && !timeToCheck.isBefore(timeExpression.getGte());
    }

    return shouldInclude;
  }

  /**
   * DateTime Expression
   */
  public static <T extends Filter> boolean evaluateDateTimeExpression(T filter, Function<T, DateTimeExpression> expressionFunc, LocalDateTime dateToCheck) {
    OffsetDateTime offsetDateToCheck = (dateToCheck == null ? null : dateToCheck.atOffset(ZoneOffset.UTC));
    return evaluateDateTimeExpression(filter, expressionFunc, offsetDateToCheck);
  }

  public static <T extends Filter> boolean evaluateDateTimeExpression(T filter, Function<T, DateTimeExpression> expressionFunc, OffsetDateTime dateToCheck) {
    if (filter == null) {
      return true;
    }

    DateTimeExpression dateTimeExpression = expressionFunc.apply(filter);
    if (dateTimeExpression == null) {
      return true;
    }

    boolean shouldInclude = true;
    if (dateTimeExpression.getEq() != null) {
      shouldInclude = dateToCheck.equals(dateTimeExpression.getEq());
    }
    if (dateTimeExpression.getNe() != null) {
      shouldInclude = !dateToCheck.equals(dateTimeExpression.getNe());
    }
    if (dateTimeExpression.getLt() != null) {
      shouldInclude = shouldInclude && dateToCheck.isBefore(dateTimeExpression.getLt());
    }
    if (dateTimeExpression.getLte() != null) {
      shouldInclude = shouldInclude && !dateToCheck.isAfter(dateTimeExpression.getLte());
    }
    if (dateTimeExpression.getGt() != null) {
      shouldInclude = shouldInclude && dateToCheck.isAfter(dateTimeExpression.getGt());
    }
    if (dateTimeExpression.getGte() != null) {
      shouldInclude = shouldInclude && !dateToCheck.isBefore(dateTimeExpression.getGte());
    }

    return shouldInclude;
  }

  /**
   * Int Expression
   */
  public static <T extends Filter> boolean evaluateIntExpression(T filter, Function<T, IntExpression> expressionFunc, Integer intToCheck) {
    if (filter == null) {
      return true;
    }

    IntExpression intExpression = expressionFunc.apply(filter);
    if (intExpression == null) {
      return true;
    }

    boolean shouldInclude = true;
    if (intExpression.getEq() != null) {
      shouldInclude = intToCheck.equals(intExpression.getEq());
    }
    if (intExpression.getNe() != null) {
      shouldInclude = shouldInclude && !intToCheck.equals(intExpression.getNe());
    }
    if (intExpression.getLt() != null) {
      shouldInclude = shouldInclude && intToCheck < intExpression.getLt();
    }
    if (intExpression.getLte() != null) {
      shouldInclude = shouldInclude && intToCheck <= intExpression.getLte();
    }
    if (intExpression.getGt() != null) {
      shouldInclude = shouldInclude && intToCheck > intExpression.getGt();
    }
    if (intExpression.getGte() != null) {
      shouldInclude = shouldInclude && intToCheck >= intExpression.getGte();
    }
    if (intExpression.getIn() != null) {
      shouldInclude = shouldInclude && intExpression.getIn().contains(intToCheck);
    }
    if (intExpression.getNotIn() != null) {
      shouldInclude = shouldInclude && !intExpression.getNotIn().contains(intToCheck);
    }

    return shouldInclude;
  }

  /**
   * Int Expression
   */
  public static <T extends Filter> boolean evaluateShortExpression(T filter, Function<T, ShortExpression> expressionFunc, Short shortToCheck) {
    if (filter == null) {
      return true;
    }

    ShortExpression shortExpression = expressionFunc.apply(filter);
    if (shortExpression == null) {
      return true;
    }

    boolean shouldInclude = true;
    if (shortExpression.getEq() != null) {
      shouldInclude = shortToCheck.equals(shortExpression.getEq());
    }
    if (shortExpression.getNe() != null) {
      shouldInclude = shouldInclude && !shortToCheck.equals(shortExpression.getNe());
    }
    if (shortExpression.getLt() != null) {
      shouldInclude = shouldInclude && shortToCheck < shortExpression.getLt();
    }
    if (shortExpression.getLte() != null) {
      shouldInclude = shouldInclude && shortToCheck <= shortExpression.getLte();
    }
    if (shortExpression.getGt() != null) {
      shouldInclude = shouldInclude && shortToCheck > shortExpression.getGt();
    }
    if (shortExpression.getGte() != null) {
      shouldInclude = shouldInclude && shortToCheck >= shortExpression.getGte();
    }
    if (shortExpression.getIn() != null) {
      shouldInclude = shouldInclude && shortExpression.getIn().contains(shortToCheck);
    }
    if (shortExpression.getNotIn() != null) {
      shouldInclude = shouldInclude && !shortExpression.getNotIn().contains(shortToCheck);
    }

    return shouldInclude;
  }

  /**
   * Int Expression
   */
  public static <T extends Filter> boolean evaluateIDExpression(T filter, Function<T, IDExpression> expressionFunc, Integer idToCheck) {
    if (filter == null) {
      return true;
    }

    IDExpression expression = expressionFunc.apply(filter);
    if (expression == null) {
      return true;
    }

    boolean shouldInclude = true;
    if (expression.getEq() != null) {
      shouldInclude = idToCheck.equals(Integer.parseInt(expression.getEq()));
    }
    if (expression.getNe() != null) {
      shouldInclude = shouldInclude && !idToCheck.equals(Integer.parseInt(expression.getNe()));
    }
    if (expression.getIn() != null) {
      shouldInclude = shouldInclude && getIntList(expression.getIn()).contains(idToCheck);
    }
    if (expression.getNotIn() != null) {
      shouldInclude = shouldInclude && !getIntList(expression.getNotIn()).contains(idToCheck);
    }

    return shouldInclude;
  }

  /**
   * Int Expression
   */
  public static <T extends Filter> boolean evaluateShortIDExpression(T filter, Function<T, IDExpression> expressionFunc, Short idToCheck) {
    if (filter == null) {
      return true;
    }

    IDExpression expression = expressionFunc.apply(filter);
    if (expression == null) {
      return true;
    }

    boolean shouldInclude = true;
    if (expression.getEq() != null) {
      shouldInclude = idToCheck.equals(Short.parseShort(expression.getEq()));
    }
    if (expression.getNe() != null) {
      shouldInclude = shouldInclude && !idToCheck.equals(Short.parseShort(expression.getNe()));
    }
    if (expression.getIn() != null) {
      shouldInclude = shouldInclude && getShortList(expression.getIn()).contains(idToCheck);
    }
    if (expression.getNotIn() != null) {
      shouldInclude = shouldInclude && !getShortList(expression.getNotIn()).contains(idToCheck);
    }

    return shouldInclude;
  }

  private static List<Integer> getIntList(List<String> stringList) {
    return stringList.stream()
        .map(Integer::parseInt)
        .collect(Collectors.toList());
  }

  private static List<Short> getShortList(List<String> stringList) {
    return stringList.stream()
            .map(Short::parseShort)
            .collect(Collectors.toList());
  }

  /**
   * String Expression
   */
  public static <T extends Filter> boolean evaluateStringExpression(T filter, Function<T, StringExpression> expressionFunc, String stringToCheck) {
    if (filter == null) {
      return true;
    }

    StringExpression expression = expressionFunc.apply(filter);
    if (expression == null) {
      return true;
    }

    boolean shouldInclude = true;
    if (expression.getEq() != null) {
      shouldInclude = expression.getEq().equals(stringToCheck);
    }
    if (expression.getNe() != null) {
      shouldInclude = !expression.getNe().equals(stringToCheck);
    }
    if (expression.getContains() != null) {
      shouldInclude = shouldInclude && expression.getContains().contains(stringToCheck);
    }

    return shouldInclude;
  }

  /**
   * Boolean Expression
   */
  public static <T extends Filter> boolean evaluateBooleanExpression(T filter, Function<T, BooleanExpression> expressionFunc, Boolean booleanToCheck) {
    if (filter == null) {
      return true;
    }

    BooleanExpression booleanExpression = expressionFunc.apply(filter);
    if (booleanExpression == null) {
      return true;
    }

    boolean shouldInclude = true;
    if (booleanExpression.getEq() != null) {
      shouldInclude = booleanExpression.getEq().equals(booleanToCheck);
    }
    if (booleanExpression.getNe() != null) {
      shouldInclude = !booleanExpression.getNe().equals(booleanToCheck);
    }

    return shouldInclude;
  }

  /**
   * ResourceType Expression
   */
  public static <T extends Filter> boolean evaluateResourceTypeExpression(T filter, Function<T, ResourceTypeExpression> expressionFunc,
                                                                          Short resourceTypeCodeToCheck) {
    ResourceType resourceTypeToCheck = (resourceTypeCodeToCheck == null ? null : ResourceType.getByResourceType(resourceTypeCodeToCheck));
    return evaluateResourceTypeExpression(filter, expressionFunc, resourceTypeToCheck);
  }

  public static <T extends Filter> boolean evaluateResourceTypeExpression(T filter, Function<T, ResourceTypeExpression> expressionFunc, ResourceType resourceTypeToCheck) {
    if (filter == null) {
      return true;
    }

    ResourceTypeExpression expression = expressionFunc.apply(filter);
    if (expression == null) {
      return true;
    }

    boolean shouldInclude = true;
    if (expression.getEq() != null) {
      ResourceType resourceType = expression.getEq();
      shouldInclude = resourceTypeToCheck.equals(resourceType);
    }
    if (expression.getNe() != null) {
      ResourceType resourceType = expression.getNe();
      shouldInclude = shouldInclude && !resourceTypeToCheck.equals(resourceType);
    }
    if (expression.getIn() != null) {
      shouldInclude = shouldInclude && expression.getIn().stream()
          .filter(Objects::nonNull)
          .anyMatch(lt -> lt.equals(resourceTypeToCheck));
    }
    if (expression.getNotIn() != null) {
      shouldInclude = shouldInclude && expression.getNotIn().stream()
          .filter(Objects::nonNull)
          .noneMatch(lt -> lt.equals(resourceTypeToCheck));
    }

    return shouldInclude;
  }
}
