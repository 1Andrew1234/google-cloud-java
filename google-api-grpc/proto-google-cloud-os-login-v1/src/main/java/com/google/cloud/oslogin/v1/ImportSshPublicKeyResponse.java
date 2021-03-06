// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/cloud/oslogin/v1/oslogin.proto

package com.google.cloud.oslogin.v1;

/**
 * <pre>
 * A response message for importing an SSH public key.
 * </pre>
 *
 * Protobuf type {@code google.cloud.oslogin.v1.ImportSshPublicKeyResponse}
 */
public  final class ImportSshPublicKeyResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:google.cloud.oslogin.v1.ImportSshPublicKeyResponse)
    ImportSshPublicKeyResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ImportSshPublicKeyResponse.newBuilder() to construct.
  private ImportSshPublicKeyResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ImportSshPublicKeyResponse() {
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ImportSshPublicKeyResponse(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!parseUnknownFieldProto3(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
          case 10: {
            com.google.cloud.oslogin.v1.LoginProfile.Builder subBuilder = null;
            if (loginProfile_ != null) {
              subBuilder = loginProfile_.toBuilder();
            }
            loginProfile_ = input.readMessage(com.google.cloud.oslogin.v1.LoginProfile.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(loginProfile_);
              loginProfile_ = subBuilder.buildPartial();
            }

            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.google.cloud.oslogin.v1.OsLoginProto.internal_static_google_cloud_oslogin_v1_ImportSshPublicKeyResponse_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.cloud.oslogin.v1.OsLoginProto.internal_static_google_cloud_oslogin_v1_ImportSshPublicKeyResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse.class, com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse.Builder.class);
  }

  public static final int LOGIN_PROFILE_FIELD_NUMBER = 1;
  private com.google.cloud.oslogin.v1.LoginProfile loginProfile_;
  /**
   * <pre>
   * The login profile information for the user.
   * </pre>
   *
   * <code>.google.cloud.oslogin.v1.LoginProfile login_profile = 1;</code>
   */
  public boolean hasLoginProfile() {
    return loginProfile_ != null;
  }
  /**
   * <pre>
   * The login profile information for the user.
   * </pre>
   *
   * <code>.google.cloud.oslogin.v1.LoginProfile login_profile = 1;</code>
   */
  public com.google.cloud.oslogin.v1.LoginProfile getLoginProfile() {
    return loginProfile_ == null ? com.google.cloud.oslogin.v1.LoginProfile.getDefaultInstance() : loginProfile_;
  }
  /**
   * <pre>
   * The login profile information for the user.
   * </pre>
   *
   * <code>.google.cloud.oslogin.v1.LoginProfile login_profile = 1;</code>
   */
  public com.google.cloud.oslogin.v1.LoginProfileOrBuilder getLoginProfileOrBuilder() {
    return getLoginProfile();
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (loginProfile_ != null) {
      output.writeMessage(1, getLoginProfile());
    }
    unknownFields.writeTo(output);
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (loginProfile_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getLoginProfile());
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse)) {
      return super.equals(obj);
    }
    com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse other = (com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse) obj;

    boolean result = true;
    result = result && (hasLoginProfile() == other.hasLoginProfile());
    if (hasLoginProfile()) {
      result = result && getLoginProfile()
          .equals(other.getLoginProfile());
    }
    result = result && unknownFields.equals(other.unknownFields);
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (hasLoginProfile()) {
      hash = (37 * hash) + LOGIN_PROFILE_FIELD_NUMBER;
      hash = (53 * hash) + getLoginProfile().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * A response message for importing an SSH public key.
   * </pre>
   *
   * Protobuf type {@code google.cloud.oslogin.v1.ImportSshPublicKeyResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:google.cloud.oslogin.v1.ImportSshPublicKeyResponse)
      com.google.cloud.oslogin.v1.ImportSshPublicKeyResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.google.cloud.oslogin.v1.OsLoginProto.internal_static_google_cloud_oslogin_v1_ImportSshPublicKeyResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.cloud.oslogin.v1.OsLoginProto.internal_static_google_cloud_oslogin_v1_ImportSshPublicKeyResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse.class, com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse.Builder.class);
    }

    // Construct using com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      if (loginProfileBuilder_ == null) {
        loginProfile_ = null;
      } else {
        loginProfile_ = null;
        loginProfileBuilder_ = null;
      }
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.google.cloud.oslogin.v1.OsLoginProto.internal_static_google_cloud_oslogin_v1_ImportSshPublicKeyResponse_descriptor;
    }

    public com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse getDefaultInstanceForType() {
      return com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse.getDefaultInstance();
    }

    public com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse build() {
      com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse buildPartial() {
      com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse result = new com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse(this);
      if (loginProfileBuilder_ == null) {
        result.loginProfile_ = loginProfile_;
      } else {
        result.loginProfile_ = loginProfileBuilder_.build();
      }
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse) {
        return mergeFrom((com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse other) {
      if (other == com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse.getDefaultInstance()) return this;
      if (other.hasLoginProfile()) {
        mergeLoginProfile(other.getLoginProfile());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private com.google.cloud.oslogin.v1.LoginProfile loginProfile_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.cloud.oslogin.v1.LoginProfile, com.google.cloud.oslogin.v1.LoginProfile.Builder, com.google.cloud.oslogin.v1.LoginProfileOrBuilder> loginProfileBuilder_;
    /**
     * <pre>
     * The login profile information for the user.
     * </pre>
     *
     * <code>.google.cloud.oslogin.v1.LoginProfile login_profile = 1;</code>
     */
    public boolean hasLoginProfile() {
      return loginProfileBuilder_ != null || loginProfile_ != null;
    }
    /**
     * <pre>
     * The login profile information for the user.
     * </pre>
     *
     * <code>.google.cloud.oslogin.v1.LoginProfile login_profile = 1;</code>
     */
    public com.google.cloud.oslogin.v1.LoginProfile getLoginProfile() {
      if (loginProfileBuilder_ == null) {
        return loginProfile_ == null ? com.google.cloud.oslogin.v1.LoginProfile.getDefaultInstance() : loginProfile_;
      } else {
        return loginProfileBuilder_.getMessage();
      }
    }
    /**
     * <pre>
     * The login profile information for the user.
     * </pre>
     *
     * <code>.google.cloud.oslogin.v1.LoginProfile login_profile = 1;</code>
     */
    public Builder setLoginProfile(com.google.cloud.oslogin.v1.LoginProfile value) {
      if (loginProfileBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        loginProfile_ = value;
        onChanged();
      } else {
        loginProfileBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <pre>
     * The login profile information for the user.
     * </pre>
     *
     * <code>.google.cloud.oslogin.v1.LoginProfile login_profile = 1;</code>
     */
    public Builder setLoginProfile(
        com.google.cloud.oslogin.v1.LoginProfile.Builder builderForValue) {
      if (loginProfileBuilder_ == null) {
        loginProfile_ = builderForValue.build();
        onChanged();
      } else {
        loginProfileBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <pre>
     * The login profile information for the user.
     * </pre>
     *
     * <code>.google.cloud.oslogin.v1.LoginProfile login_profile = 1;</code>
     */
    public Builder mergeLoginProfile(com.google.cloud.oslogin.v1.LoginProfile value) {
      if (loginProfileBuilder_ == null) {
        if (loginProfile_ != null) {
          loginProfile_ =
            com.google.cloud.oslogin.v1.LoginProfile.newBuilder(loginProfile_).mergeFrom(value).buildPartial();
        } else {
          loginProfile_ = value;
        }
        onChanged();
      } else {
        loginProfileBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <pre>
     * The login profile information for the user.
     * </pre>
     *
     * <code>.google.cloud.oslogin.v1.LoginProfile login_profile = 1;</code>
     */
    public Builder clearLoginProfile() {
      if (loginProfileBuilder_ == null) {
        loginProfile_ = null;
        onChanged();
      } else {
        loginProfile_ = null;
        loginProfileBuilder_ = null;
      }

      return this;
    }
    /**
     * <pre>
     * The login profile information for the user.
     * </pre>
     *
     * <code>.google.cloud.oslogin.v1.LoginProfile login_profile = 1;</code>
     */
    public com.google.cloud.oslogin.v1.LoginProfile.Builder getLoginProfileBuilder() {
      
      onChanged();
      return getLoginProfileFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     * The login profile information for the user.
     * </pre>
     *
     * <code>.google.cloud.oslogin.v1.LoginProfile login_profile = 1;</code>
     */
    public com.google.cloud.oslogin.v1.LoginProfileOrBuilder getLoginProfileOrBuilder() {
      if (loginProfileBuilder_ != null) {
        return loginProfileBuilder_.getMessageOrBuilder();
      } else {
        return loginProfile_ == null ?
            com.google.cloud.oslogin.v1.LoginProfile.getDefaultInstance() : loginProfile_;
      }
    }
    /**
     * <pre>
     * The login profile information for the user.
     * </pre>
     *
     * <code>.google.cloud.oslogin.v1.LoginProfile login_profile = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.cloud.oslogin.v1.LoginProfile, com.google.cloud.oslogin.v1.LoginProfile.Builder, com.google.cloud.oslogin.v1.LoginProfileOrBuilder> 
        getLoginProfileFieldBuilder() {
      if (loginProfileBuilder_ == null) {
        loginProfileBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.google.cloud.oslogin.v1.LoginProfile, com.google.cloud.oslogin.v1.LoginProfile.Builder, com.google.cloud.oslogin.v1.LoginProfileOrBuilder>(
                getLoginProfile(),
                getParentForChildren(),
                isClean());
        loginProfile_ = null;
      }
      return loginProfileBuilder_;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFieldsProto3(unknownFields);
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:google.cloud.oslogin.v1.ImportSshPublicKeyResponse)
  }

  // @@protoc_insertion_point(class_scope:google.cloud.oslogin.v1.ImportSshPublicKeyResponse)
  private static final com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse();
  }

  public static com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ImportSshPublicKeyResponse>
      PARSER = new com.google.protobuf.AbstractParser<ImportSshPublicKeyResponse>() {
    public ImportSshPublicKeyResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new ImportSshPublicKeyResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ImportSshPublicKeyResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ImportSshPublicKeyResponse> getParserForType() {
    return PARSER;
  }

  public com.google.cloud.oslogin.v1.ImportSshPublicKeyResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

