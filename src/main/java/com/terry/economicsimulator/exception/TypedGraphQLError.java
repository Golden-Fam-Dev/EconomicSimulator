package com.terry.economicsimulator.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.execution.ResultPath;
import graphql.language.SourceLocation;

import java.util.*;

import static graphql.Assert.assertNotNull;

public class TypedGraphQLError implements GraphQLError {
  private final String message;
  private final List<SourceLocation> locations;
  private final transient List<Object> path;
  private final transient Map<String, Object> extensions;

  @JsonCreator
  public TypedGraphQLError(@JsonProperty("message") String message, @JsonProperty("locations") List<SourceLocation> locations, @JsonProperty("path") List<Object> path, @JsonProperty("extensions") Map<String, Object> extensions) {
    this.message = message;
    this.locations = locations;
    this.path = path;
    this.extensions = extensions;
  }

  public String getMessage() {
    return this.message;
  }

  public List<SourceLocation> getLocations() {
    return this.locations;
  }

  public ErrorClassification getErrorType() {
    return null;
  }

  @Override
  public List<Object> getPath() {
    return this.path;
  }

  @Override
  public Map<String, Object> getExtensions() {
    return this.extensions;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static Builder newInternalErrorBuilder() {
    return (new Builder()).errorType(ErrorType.INTERNAL);
  }

  public static Builder newNotFoundBuilder() {
    return (new Builder()).errorType(ErrorType.NOT_FOUND);
  }

  public static Builder newPermissionDeniedBuilder() {
    return (new Builder()).errorType(ErrorType.PERMISSION_DENIED);
  }

  public static Builder newBadRequestBuilder() {
    return (new Builder()).errorType(ErrorType.BAD_REQUEST);
  }

  public static Builder newConflictBuilder() {
    return (new Builder()).errorDetail(ErrorDetail.Common.CONFLICT);
  }

  public String toString() {
    return "TypedGraphQLError{message='" + this.message + "', locations=" + this.locations + ", path=" + this.path + ", extensions=" + this.extensions + "}";
  }

  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    } else if (obj == null) {
      return false;
    } else if (obj.getClass() != this.getClass()) {
      return false;
    } else {
      TypedGraphQLError e = (TypedGraphQLError)obj;
      if (!Objects.equals(this.message, e.message)) {
        return false;
      } else if (!Objects.equals(this.locations, e.locations)) {
        return false;
      } else if (!Objects.equals(this.path, e.path)) {
        return false;
      } else {
        return Objects.equals(this.extensions, e.extensions);
      }
    }
  }

  public int hashCode() {
    return super.hashCode();
  }

  public static class Builder {
    private String message;
    private List<Object> path;
    private final List<SourceLocation> locations = new ArrayList<>();
    private ErrorClassification errorClassification;
    private Map<String, Object> extensions;
    private String origin;
    private String debugUri;
    private Map<String, Object> debugInfo;

    private Builder() {
      this.errorClassification = ErrorType.UNKNOWN;
    }

    private String defaultMessage() {
      return this.errorClassification.toString();
    }

    private Map<String, Object> getExtensions() {
      HashMap<String, Object> extensionsMap = new HashMap<>();
      if (this.extensions != null) {
        extensionsMap.putAll(this.extensions);
      }

      if (this.errorClassification instanceof ErrorType) {
        extensionsMap.put("errorType", String.valueOf(this.errorClassification));
      } else if (this.errorClassification instanceof ErrorDetail anErrorClassification) {
        extensionsMap.put("errorType", String.valueOf(anErrorClassification.getErrorType()));
        extensionsMap.put("errorDetail", String.valueOf(this.errorClassification));
      }

      if (this.origin != null) {
        extensionsMap.put("origin", this.origin);
      }

      if (this.debugUri != null) {
        extensionsMap.put("debugUri", this.debugUri);
      }

      if (this.debugInfo != null) {
        extensionsMap.put("debugInfo", this.debugInfo);
      }

      return extensionsMap;
    }

    public Builder message(String message, Object... formatArgs) {
      this.message = String.format(assertNotNull(message), formatArgs);
      return this;
    }

    public Builder locations(List<SourceLocation> locations) {
      this.locations.addAll(assertNotNull(locations));
      return this;
    }

    public Builder location(SourceLocation location) {
      this.locations.add(assertNotNull(location));
      return this;
    }

    public Builder path(ResultPath path) {
      this.path = (assertNotNull(path)).toList();
      return this;
    }

    public Builder path(List<Object> path) {
      this.path = assertNotNull(path);
      return this;
    }

    public Builder errorType(ErrorType errorType) {
      this.errorClassification = assertNotNull(errorType);
      return this;
    }

    public Builder errorDetail(ErrorDetail errorDetail) {
      this.errorClassification = assertNotNull(errorDetail);
      return this;
    }

    public Builder origin(String origin) {
      this.origin = assertNotNull(origin);
      return this;
    }

    public Builder debugUri(String debugUri) {
      this.debugUri = assertNotNull(debugUri);
      return this;
    }

    public Builder debugInfo(Map<String, Object> debugInfo) {
      this.debugInfo = assertNotNull(debugInfo);
      return this;
    }

    public Builder extensions(Map<String, Object> extensions) {
      this.extensions = assertNotNull(extensions);
      return this;
    }

    public TypedGraphQLError build() {
      if (this.message == null) {
        this.message = this.defaultMessage();
      }

      return new TypedGraphQLError(this.message, this.locations, this.path, this.getExtensions());
    }
  }
}
