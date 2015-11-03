/*
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.gcloud.storage;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gcloud.AuthCredentials.ServiceAccountAuthCredentials;
import com.google.gcloud.Service;
import com.google.gcloud.Page;
import com.google.gcloud.spi.StorageRpc;
import com.google.gcloud.spi.StorageRpc.Tuple;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * An interface for Google Cloud Storage.
 *
 * @see <a href="https://cloud.google.com/storage/docs">Google Cloud Storage</a>
 */
public interface Storage extends Service<StorageOptions> {

  public static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";

  enum PredefinedAcl {
    AUTHENTICATED_READ("authenticatedRead"),
    ALL_AUTHENTICATED_USERS("allAuthenticatedUsers"),
    PRIVATE("private"),
    PROJECT_PRIVATE("projectPrivate"),
    PUBLIC_READ("publicRead"),
    PUBLIC_READ_WRITE("publicReadWrite"),
    BUCKET_OWNER_READ("bucketOwnerRead"),
    BUCKET_OWNER_FULL_CONTROL("bucketOwnerFullControl");

    private final String entry;

    PredefinedAcl(String entry) {
      this.entry = entry;
    }

    String entry() {
      return entry;
    }
  }

  public static abstract class EntityField {

    private final String selector;

    public EntityField(String selector) {
      this.selector = selector;
    }

    public String selector() {
      return selector;
    }
  }

  enum BucketField {
    ID("id"),
    SELF_LINK("selfLink"),
    NAME("name"),
    TIME_CREATED("timeCreated"),
    UPDATED("updated"),
    METAGENERATION("metageneration"),
    ACL("acl"),
    DEFAULT_OBJECT_ACL("defaultObjectAcl"),
    OWNER("owner"),
    LOCATION("location"),
    WEBSITE("website"),
    VERSIONING("versioning"),
    CORS("cors"),
    STORAGE_CLASS("storageClass"),
    ETAG("etag");

    private final String selector;

    BucketField(String selector) {
      this.selector = selector;
    }

    public String selector() {
      return selector;
    }

    static String selector(BucketField... fields) {
      HashSet<String> fieldStrings = Sets.newHashSetWithExpectedSize(fields.length + 2);
      fieldStrings.add(NAME.selector());
      for (BucketField field : fields) {
        fieldStrings.add(field.selector());
      }
      return Joiner.on(',').join(fieldStrings);
    }
  }

  enum BlobField {
    ACL("acl"),
    BUCKET("bucket"),
    CACHE_CONTROL("cacheControl"),
    COMPONENT_COUNT("componentCount"),
    CONTENT_DISPOSITION("contentDisposition"),
    CONTENT_ENCODING("contentEncoding"),
    CONTENT_LANGUAGE("contentLanguage"),
    CONTENT_TYPE("contentType"),
    CRC32C("crc32c"),
    ETAG("etag"),
    GENERATION("generation"),
    ID("id"),
    KIND("kind"),
    MD5HASH("md5Hash"),
    MEDIA_LINK("mediaLink"),
    METADATA("metadata"),
    METAGENERATION("metageneration"),
    NAME("name"),
    OWNER("owner"),
    SELF_LINK("selfLink"),
    SIZE("size"),
    STORAGE_CLASS("storageClass"),
    TIME_CREATED("timeCreated"),
    TIME_DELETED("timeDeleted"),
    UPDATED("updated");

    private final String selector;

    BlobField(String selector) {
      this.selector = selector;
    }

    public String selector() {
      return selector;
    }

    static String selector(BlobField... fields) {
      HashSet<String> fieldStrings = Sets.newHashSetWithExpectedSize(fields.length + 2);
      fieldStrings.add(BUCKET.selector());
      fieldStrings.add(NAME.selector());
      for (BlobField field : fields) {
        fieldStrings.add(field.selector());
      }
      return Joiner.on(',').join(fieldStrings);
    }
  }

  class BucketTargetOption extends Option {

    private static final long serialVersionUID = -5880204616982900975L;

    private BucketTargetOption(StorageRpc.Option rpcOption, Object value) {
      super(rpcOption, value);
    }

    private BucketTargetOption(StorageRpc.Option rpcOption) {
      this(rpcOption, null);
    }

    public static BucketTargetOption predefinedAcl(PredefinedAcl acl) {
      return new BucketTargetOption(StorageRpc.Option.PREDEFINED_ACL, acl.entry());
    }

    public static BucketTargetOption predefinedDefaultObjectAcl(PredefinedAcl acl) {
      return new BucketTargetOption(StorageRpc.Option.PREDEFINED_DEFAULT_OBJECT_ACL, acl.entry());
    }

    public static BucketTargetOption metagenerationMatch() {
      return new BucketTargetOption(StorageRpc.Option.IF_METAGENERATION_MATCH);
    }

    public static BucketTargetOption metagenerationNotMatch() {
      return new BucketTargetOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH);
    }
  }

  class BucketSourceOption extends Option {

    private static final long serialVersionUID = 5185657617120212117L;

    private BucketSourceOption(StorageRpc.Option rpcOption, long metageneration) {
      super(rpcOption, metageneration);
    }

    public static BucketSourceOption metagenerationMatch(long metageneration) {
      return new BucketSourceOption(StorageRpc.Option.IF_METAGENERATION_MATCH, metageneration);
    }

    public static BucketSourceOption metagenerationNotMatch(long metageneration) {
      return new BucketSourceOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH, metageneration);
    }
  }

  class BucketGetOption extends Option {

    private static final long serialVersionUID = 1901844869484087395L;

    private BucketGetOption(StorageRpc.Option rpcOption, long metageneration) {
      super(rpcOption, metageneration);
    }

    private BucketGetOption(StorageRpc.Option rpcOption, String value) {
      super(rpcOption, value);
    }

    public static BucketGetOption metagenerationMatch(long metageneration) {
      return new BucketGetOption(StorageRpc.Option.IF_METAGENERATION_MATCH, metageneration);
    }

    public static BucketGetOption metagenerationNotMatch(long metageneration) {
      return new BucketGetOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH, metageneration);
    }

    /**
     * Returns an option to specify the bucket's fields to be returned by the RPC call. If this
     * option is not provided all bucket's fields are returned. {@code BucketGetOption.fields}) can
     * be used to specify only the fields of interest. Bucket name is always returned, even if not
     * specified.
     */
    public static BucketGetOption fields(BucketField... fields) {
      return new BucketGetOption(StorageRpc.Option.FIELDS, BucketField.selector(fields));
    }
  }

  class BlobTargetOption extends Option {

    private static final long serialVersionUID = 214616862061934846L;

    private BlobTargetOption(StorageRpc.Option rpcOption, Object value) {
      super(rpcOption, value);
    }

    private BlobTargetOption(StorageRpc.Option rpcOption) {
      this(rpcOption, null);
    }

    public static BlobTargetOption predefinedAcl(PredefinedAcl acl) {
      return new BlobTargetOption(StorageRpc.Option.PREDEFINED_ACL, acl.entry());
    }

    public static BlobTargetOption doesNotExist() {
      return new BlobTargetOption(StorageRpc.Option.IF_GENERATION_MATCH, 0L);
    }

    public static BlobTargetOption generationMatch() {
      return new BlobTargetOption(StorageRpc.Option.IF_GENERATION_MATCH);
    }

    public static BlobTargetOption generationNotMatch() {
      return new BlobTargetOption(StorageRpc.Option.IF_GENERATION_NOT_MATCH);
    }

    public static BlobTargetOption metagenerationMatch() {
      return new BlobTargetOption(StorageRpc.Option.IF_METAGENERATION_MATCH);
    }

    public static BlobTargetOption metagenerationNotMatch() {
      return new BlobTargetOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH);
    }

    static Tuple<BlobInfo, BlobTargetOption[]> convert(BlobInfo info, BlobWriteOption... options) {
      BlobInfo.Builder infoBuilder = info.toBuilder().crc32c(null).md5(null);
      List<BlobTargetOption> targetOptions = Lists.newArrayListWithCapacity(options.length);
      for (BlobWriteOption option : options) {
        switch (option.option) {
          case IF_CRC32C_MATCH:
            infoBuilder.crc32c(info.crc32c());
            break;
          case IF_MD5_MATCH:
            infoBuilder.md5(info.md5());
            break;
          default:
            targetOptions.add(option.toTargetOption());
            break;
        }
      }
      return Tuple.of(infoBuilder.build(),
          targetOptions.toArray(new BlobTargetOption[targetOptions.size()]));
    }
  }

  class BlobWriteOption implements Serializable {

    private static final long serialVersionUID = -3880421670966224580L;

    private final Option option;
    private final Object value;

    enum Option {
      PREDEFINED_ACL, IF_GENERATION_MATCH, IF_GENERATION_NOT_MATCH, IF_METAGENERATION_MATCH,
      IF_METAGENERATION_NOT_MATCH, IF_MD5_MATCH, IF_CRC32C_MATCH;

      StorageRpc.Option toRpcOption() {
        return StorageRpc.Option.valueOf(this.name());
      }
    }

    BlobTargetOption toTargetOption() {
      return new BlobTargetOption(this.option.toRpcOption(), this.value);
    }

    private BlobWriteOption(Option option, Object value) {
      this.option = option;
      this.value = value;
    }

    private BlobWriteOption(Option option) {
      this(option, null);
    }

    @Override
    public int hashCode() {
      return Objects.hash(option, value);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == null) {
        return false;
      }
      if (!(obj instanceof BlobWriteOption)) {
        return false;
      }
      final BlobWriteOption other = (BlobWriteOption) obj;
      return this.option == other.option && Objects.equals(this.value, other.value);
    }

    public static BlobWriteOption predefinedAcl(PredefinedAcl acl) {
      return new BlobWriteOption(Option.PREDEFINED_ACL, acl.entry());
    }

    public static BlobWriteOption doesNotExist() {
      return new BlobWriteOption(Option.IF_GENERATION_MATCH, 0L);
    }

    public static BlobWriteOption generationMatch() {
      return new BlobWriteOption(Option.IF_GENERATION_MATCH);
    }

    public static BlobWriteOption generationNotMatch() {
      return new BlobWriteOption(Option.IF_GENERATION_NOT_MATCH);
    }

    public static BlobWriteOption metagenerationMatch() {
      return new BlobWriteOption(Option.IF_METAGENERATION_MATCH);
    }

    public static BlobWriteOption metagenerationNotMatch() {
      return new BlobWriteOption(Option.IF_METAGENERATION_NOT_MATCH);
    }

    public static BlobWriteOption md5Match() {
      return new BlobWriteOption(Option.IF_MD5_MATCH, true);
    }

    public static BlobWriteOption crc32cMatch() {
      return new BlobWriteOption(Option.IF_CRC32C_MATCH, true);
    }
  }

  class BlobSourceOption extends Option {

    private static final long serialVersionUID = -3712768261070182991L;

    private BlobSourceOption(StorageRpc.Option rpcOption, long value) {
      super(rpcOption, value);
    }

    public static BlobSourceOption generationMatch(long generation) {
      return new BlobSourceOption(StorageRpc.Option.IF_GENERATION_MATCH, generation);
    }

    public static BlobSourceOption generationNotMatch(long generation) {
      return new BlobSourceOption(StorageRpc.Option.IF_GENERATION_NOT_MATCH, generation);
    }

    public static BlobSourceOption metagenerationMatch(long metageneration) {
      return new BlobSourceOption(StorageRpc.Option.IF_METAGENERATION_MATCH, metageneration);
    }

    public static BlobSourceOption metagenerationNotMatch(long metageneration) {
      return new BlobSourceOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH, metageneration);
    }
  }

  class BlobGetOption extends Option {

    private static final long serialVersionUID = 803817709703661480L;

    private BlobGetOption(StorageRpc.Option rpcOption, long value) {
      super(rpcOption, value);
    }

    private BlobGetOption(StorageRpc.Option rpcOption, String value) {
      super(rpcOption, value);
    }

    public static BlobGetOption generationMatch(long generation) {
      return new BlobGetOption(StorageRpc.Option.IF_GENERATION_MATCH, generation);
    }

    public static BlobGetOption generationNotMatch(long generation) {
      return new BlobGetOption(StorageRpc.Option.IF_GENERATION_NOT_MATCH, generation);
    }

    public static BlobGetOption metagenerationMatch(long metageneration) {
      return new BlobGetOption(StorageRpc.Option.IF_METAGENERATION_MATCH, metageneration);
    }

    public static BlobGetOption metagenerationNotMatch(long metageneration) {
      return new BlobGetOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH, metageneration);
    }

    /**
     * Returns an option to specify the blob's fields to be returned by the RPC call. If this option
     * is not provided all blob's fields are returned. {@code BlobGetOption.fields}) can be used to
     * specify only the fields of interest. Blob name and bucket are always returned, even if not
     * specified.
     */
    public static BlobGetOption fields(BlobField... fields) {
      return new BlobGetOption(StorageRpc.Option.FIELDS, BlobField.selector(fields));
    }
  }

  class BucketListOption extends Option {

    private static final long serialVersionUID = 8754017079673290353L;

    private BucketListOption(StorageRpc.Option option, Object value) {
      super(option, value);
    }

    public static BucketListOption maxResults(long maxResults) {
      return new BucketListOption(StorageRpc.Option.MAX_RESULTS, maxResults);
    }

    public static BucketListOption startPageToken(String pageToken) {
      return new BucketListOption(StorageRpc.Option.PAGE_TOKEN, pageToken);
    }

    public static BucketListOption prefix(String prefix) {
      return new BucketListOption(StorageRpc.Option.PREFIX, prefix);
    }

    /**
     * Returns an option to specify the bucket's fields to be returned by the RPC call. If this
     * option is not provided all bucket's fields are returned. {@code BucketListOption.fields}) can
     * be used to specify only the fields of interest. Bucket name is always returned, even if not
     * specified.
     */
    public static BucketListOption fields(BucketField... fields) {
      StringBuilder builder = new StringBuilder();
      builder.append("items(").append(BucketField.selector(fields)).append(")");
      return new BucketListOption(StorageRpc.Option.FIELDS, builder.toString());
    }
  }

  class BlobListOption extends Option {

    private static final long serialVersionUID = 9083383524788661294L;

    private BlobListOption(StorageRpc.Option option, Object value) {
      super(option, value);
    }

    public static BlobListOption maxResults(long maxResults) {
      return new BlobListOption(StorageRpc.Option.MAX_RESULTS, maxResults);
    }

    public static BlobListOption startPageToken(String pageToken) {
      return new BlobListOption(StorageRpc.Option.PAGE_TOKEN, pageToken);
    }

    public static BlobListOption prefix(String prefix) {
      return new BlobListOption(StorageRpc.Option.PREFIX, prefix);
    }

    public static BlobListOption recursive(boolean recursive) {
      return new BlobListOption(StorageRpc.Option.DELIMITER, recursive);
    }

    /**
     * Returns an option to specify the blob's fields to be returned by the RPC call. If this option
     * is not provided all blob's fields are returned. {@code BlobListOption.fields}) can be used to
     * specify only the fields of interest. Blob name and bucket are always returned, even if not
     * specified.
     */
    public static BlobListOption fields(BlobField... fields) {
      StringBuilder builder = new StringBuilder();
      builder.append("items(").append(BlobField.selector(fields)).append(")");
      return new BlobListOption(StorageRpc.Option.FIELDS, builder.toString());
    }
  }

  class SignUrlOption implements Serializable {

    private static final long serialVersionUID = 7850569877451099267L;

    private final Option option;
    private final Object value;

    enum Option {
      HTTP_METHOD, CONTENT_TYPE, MD5, SERVICE_ACCOUNT_CRED
    }

    private SignUrlOption(Option option, Object value) {
      this.option = option;
      this.value = value;
    }

    Option option() {
      return option;
    }

    Object value() {
      return value;
    }

    /**
     * The HTTP method to be used with the signed URL.
     */
    public static SignUrlOption httpMethod(HttpMethod httpMethod) {
      return new SignUrlOption(Option.HTTP_METHOD, httpMethod.name());
    }

    /**
     * Use it if signature should include the blob's content-type.
     * When used, users of the signed URL should include the blob's content-type with their request.
     */
    public static SignUrlOption withContentType() {
      return new SignUrlOption(Option.CONTENT_TYPE, true);
    }

    /**
     * Use it if signature should include the blob's md5.
     * When used, users of the signed URL should include the blob's md5 with their request.
     */
    public static SignUrlOption withMd5() {
      return new SignUrlOption(Option.MD5, true);
    }

    /**
     * Service account credentials which are used for signing the URL.
     * If not provided an attempt will be made to get it from the environment.
     *
     * @see <a href="https://cloud.google.com/storage/docs/authentication#service_accounts">Service
     *     account</a>
     */
    public static SignUrlOption serviceAccount(ServiceAccountAuthCredentials credentials) {
      return new SignUrlOption(Option.SERVICE_ACCOUNT_CRED, credentials);
    }
  }

  class ComposeRequest implements Serializable {

    private static final long serialVersionUID = -7385681353748590911L;

    private final List<SourceBlob> sourceBlobs;
    private final BlobInfo target;
    private final List<BlobTargetOption> targetOptions;

    public static class SourceBlob implements Serializable {

      private static final long serialVersionUID = 4094962795951990439L;

      final String name;
      final Long generation;

      SourceBlob(String name) {
        this(name, null);
      }

      SourceBlob(String name, Long generation) {
        this.name = name;
        this.generation = generation;
      }

      public String name() {
        return name;
      }

      public Long generation() {
        return generation;
      }
    }

    public static class Builder {

      private final List<SourceBlob> sourceBlobs = new LinkedList<>();
      private final Set<BlobTargetOption> targetOptions = new LinkedHashSet<>();
      private BlobInfo target;

      public Builder addSource(Iterable<String> blobs) {
        for (String blob : blobs) {
          sourceBlobs.add(new SourceBlob(blob));
        }
        return this;
      }

      public Builder addSource(String... blobs) {
        return addSource(Arrays.asList(blobs));
      }

      /**
       * Add a source with a specific generation to match.
       */
      public Builder addSource(String blob, long generation) {
        sourceBlobs.add(new SourceBlob(blob, generation));
        return this;
      }

      public Builder target(BlobInfo target) {
        this.target = target;
        return this;
      }

      public Builder targetOptions(BlobTargetOption... options) {
        Collections.addAll(targetOptions, options);
        return this;
      }

      public Builder targetOptions(Iterable<BlobTargetOption> options) {
        Iterables.addAll(targetOptions, options);
        return this;
      }

      public ComposeRequest build() {
        checkArgument(!sourceBlobs.isEmpty());
        checkNotNull(target);
        return new ComposeRequest(this);
      }
    }

    private ComposeRequest(Builder builder) {
      sourceBlobs = ImmutableList.copyOf(builder.sourceBlobs);
      target = builder.target;
      targetOptions = ImmutableList.copyOf(builder.targetOptions);
    }

    public List<SourceBlob> sourceBlobs() {
      return sourceBlobs;
    }

    public BlobInfo target() {
      return target;
    }

    public List<BlobTargetOption> targetOptions() {
      return targetOptions;
    }

    public static ComposeRequest of(Iterable<String> sources, BlobInfo target) {
      return builder().target(target).addSource(sources).build();
    }

    public static ComposeRequest of(String bucket, Iterable<String> sources, String target) {
      return of(sources, BlobInfo.builder(BlobId.of(bucket, target)).build());
    }

    public static Builder builder() {
      return new Builder();
    }
  }

  class CopyRequest implements Serializable {

    private static final long serialVersionUID = -4498650529476219937L;

    private final BlobId source;
    private final List<BlobSourceOption> sourceOptions;
    private final BlobInfo target;
    private final List<BlobTargetOption> targetOptions;
    private final Long megabytesCopiedPerChunk;

    public static class Builder {

      private final Set<BlobSourceOption> sourceOptions = new LinkedHashSet<>();
      private final Set<BlobTargetOption> targetOptions = new LinkedHashSet<>();
      private BlobId source;
      private BlobInfo target;
      private Long megabytesCopiedPerChunk;

      /**
       * Sets the blob to copy given bucket and blob name.
       *
       * @return the builder.
       */
      public Builder source(String bucket, String blob) {
        this.source = BlobId.of(bucket, blob);
        return this;
      }

      /**
       * Sets the blob to copy given a {@link BlobId}.
       *
       * @return the builder.
       */
      public Builder source(BlobId source) {
        this.source = source;
        return this;
      }

      /**
       * Sets blob's source options.
       *
       * @return the builder.
       */
      public Builder sourceOptions(BlobSourceOption... options) {
        Collections.addAll(sourceOptions, options);
        return this;
      }

      /**
       * Sets blob's source options.
       *
       * @return the builder.
       */
      public Builder sourceOptions(Iterable<BlobSourceOption> options) {
        Iterables.addAll(sourceOptions, options);
        return this;
      }

      /**
       * Sets the copy target. Target blob information is copied from source.
       *
       * @return the builder.
       */
      public Builder target(BlobId target) {
        this.target = BlobInfo.builder(target).build();
        return this;
      }

      /**
       * Sets the copy target and target options. {@code target} parameter is used to override
       * source blob information (e.g. {@code contentType}, {@code contentLanguage}). {@code
       * target.contentType} is a required field.
       *
       * @return the builder.
       * @throws IllegalArgumentException if {@code target.contentType} is {@code null}
       */
      public Builder target(BlobInfo target, BlobTargetOption... options)
          throws IllegalArgumentException {
        checkContentType(target);
        this.target = target;
        Collections.addAll(targetOptions, options);
        return this;
      }

      /**
       * Sets the copy target and target options. {@code target} parameter is used to override
       * source blob information (e.g. {@code contentType}, {@code contentLanguage}). {@code
       * target.contentType} is a required field.
       *
       * @return the builder.
       * @throws IllegalArgumentException if {@code target.contentType} is {@code null}
       */
      public Builder target(BlobInfo target, Iterable<BlobTargetOption> options)
          throws IllegalArgumentException {
        checkContentType(target);
        this.target = target;
        Iterables.addAll(targetOptions, options);
        return this;
      }

      /**
       * Sets the maximum number of megabytes to copy for each RPC call. This parameter is ignored
       * if source and target blob share the same location and storage class as copy is made with
       * one single RPC.
       *
       * @return the builder.
       */
      public Builder megabytesCopiedPerChunk(Long megabytesCopiedPerChunk) {
        this.megabytesCopiedPerChunk = megabytesCopiedPerChunk;
        return this;
      }

      /**
       * Creates a {@code CopyRequest}.
       */
      public CopyRequest build() {
        checkNotNull(source);
        checkNotNull(target);
        return new CopyRequest(this);
      }
    }

    private CopyRequest(Builder builder) {
      source = checkNotNull(builder.source);
      sourceOptions = ImmutableList.copyOf(builder.sourceOptions);
      target = checkNotNull(builder.target);
      targetOptions = ImmutableList.copyOf(builder.targetOptions);
      megabytesCopiedPerChunk = builder.megabytesCopiedPerChunk;
    }

    /**
     * Returns the blob to rewrite, as a {@link BlobId}.
     */
    public BlobId source() {
      return source;
    }

    /**
     * Returns blob's source options.
     */
    public List<BlobSourceOption> sourceOptions() {
      return sourceOptions;
    }

    /**
     * Returns the rewrite target.
     */
    public BlobInfo target() {
      return target;
    }

    /**
     * Returns blob's target options.
     */
    public List<BlobTargetOption> targetOptions() {
      return targetOptions;
    }

    /**
     * Returns the maximum number of megabytes to copy for each RPC call. This parameter is ignored
     * if source and target blob share the same location and storage class as copy is made with
     * one single RPC.
     */
    public Long megabytesCopiedPerChunk() {
      return megabytesCopiedPerChunk;
    }

    /**
     * Creates a copy request. {@code target} parameter is used to override source blob information
     * (e.g. {@code contentType}, {@code contentLanguage}). {@code target.contentType} is a required
     * field.
     *
     * @param sourceBucket name of the bucket containing the source blob
     * @param sourceBlob name of the source blob
     * @param target a {@code BlobInfo} object for the target blob
     * @return a copy request.
     * @throws IllegalArgumentException if {@code target.contentType} is {@code null}
     */
    public static CopyRequest of(String sourceBucket, String sourceBlob, BlobInfo target)
        throws IllegalArgumentException {
      checkContentType(target);
      return builder().source(sourceBucket, sourceBlob).target(target).build();
    }

    /**
     * Creates a copy request. {@code target} parameter is used to override source blob information
     * (e.g. {@code contentType}, {@code contentLanguage}). {@code target.contentType} is a required
     * field.
     *
     * @param sourceBlobId a {@code BlobId} object for the source blob
     * @param target a {@code BlobInfo} object for the target blob
     * @return a copy request.
     * @throws IllegalArgumentException if {@code target.contentType} is {@code null}
     */
    public static CopyRequest of(BlobId sourceBlobId, BlobInfo target)
        throws IllegalArgumentException {
      checkContentType(target);
      return builder().source(sourceBlobId).target(target).build();
    }

    /**
     * Creates a copy request. Target blob information is copied from source.
     *
     * @param sourceBucket name of the bucket containing both the source and the target blob
     * @param sourceBlob name of the source blob
     * @param targetBlob name of the target blob
     * @return a copy request.
     */
    public static CopyRequest of(String sourceBucket, String sourceBlob, String targetBlob) {
      return CopyRequest.builder()
          .source(sourceBucket, sourceBlob)
          .target(BlobId.of(sourceBucket, targetBlob))
          .build();
    }

    /**
     * Creates a copy request. Target blob information is copied from source.
     *
     * @param sourceBucket name of the bucket containing the source blob
     * @param sourceBlob name of the source blob
     * @param target a {@code BlobId} object for the target blob
     * @return a copy request.
     */
    public static CopyRequest of(String sourceBucket, String sourceBlob, BlobId target) {
      return builder().source(sourceBucket, sourceBlob).target(target).build();
    }

    /**
     * Creates a copy request. Target blob information is copied from source.
     *
     * @param sourceBlobId a {@code BlobId} object for the source blob
     * @param targetBlob name of the target blob, in the same bucket of the source blob
     * @return a copy request.
     */
    public static CopyRequest of(BlobId sourceBlobId, String targetBlob) {
      return CopyRequest.builder()
          .source(sourceBlobId)
          .target(BlobId.of(sourceBlobId.bucket(), targetBlob))
          .build();
    }

    /**
     * Creates a copy request. Target blob information is copied from source.
     *
     * @param sourceBlobId a {@code BlobId} object for the source blob
     * @param targetBlobId a {@code BlobId} object for the target blob
     * @return a copy request.
     */
    public static CopyRequest of(BlobId sourceBlobId, BlobId targetBlobId) {
      return CopyRequest.builder()
          .source(sourceBlobId)
          .target(targetBlobId)
          .build();
    }

    public static Builder builder() {
      return new Builder();
    }

    private static void checkContentType(BlobInfo blobInfo) throws IllegalArgumentException {
      checkArgument(blobInfo.contentType() != null, "Blob content type can not be null");
    }
  }

  /**
   * Create a new bucket.
   *
   * @return a complete bucket information.
   * @throws StorageException upon failure
   */
  BucketInfo create(BucketInfo bucketInfo, BucketTargetOption... options);

  /**
   * Create a new blob with no content.
   *
   * @return a complete blob information.
   * @throws StorageException upon failure
   */
  BlobInfo create(BlobInfo blobInfo, BlobTargetOption... options);

  /**
   * Create a new blob. Direct upload is used to upload {@code content}. For large content,
   * {@link #writer} is recommended as it uses resumable upload. MD5 and CRC32C hashes of
   * {@code content} are computed and used for validating transferred data.
   *
   * @return a complete blob information.
   * @throws StorageException upon failure
   * @see <a href="https://cloud.google.com/storage/docs/hashes-etags">Hashes and ETags</a>
   */
  BlobInfo create(BlobInfo blobInfo, byte[] content, BlobTargetOption... options);

  /**
   * Create a new blob. Direct upload is used to upload {@code content}. For large content,
   * {@link #writer} is recommended as it uses resumable upload. By default any md5 and crc32c
   * values in the given {@code blobInfo} are ignored unless requested via the
   * {@code BlobWriteOption.md5Match} and {@code BlobWriteOption.crc32cMatch} options.
   *
   * @return a complete blob information.
   * @throws StorageException upon failure
   */
  BlobInfo create(BlobInfo blobInfo, InputStream content, BlobWriteOption... options);

  /**
   * Return the requested bucket or {@code null} if not found.
   *
   * @throws StorageException upon failure
   */
  BucketInfo get(String bucket, BucketGetOption... options);

  /**
   * Return the requested blob or {@code null} if not found.
   *
   * @throws StorageException upon failure
   */
  BlobInfo get(String bucket, String blob, BlobGetOption... options);

  /**
   * Return the requested blob or {@code null} if not found.
   *
   * @throws StorageException upon failure
   */
  BlobInfo get(BlobId blob, BlobGetOption... options);

  /**
   * Return the requested blob or {@code null} if not found.
   *
   * @throws StorageException upon failure
   */
  BlobInfo get(BlobId blob);

  /**
   * List the project's buckets.
   *
   * @throws StorageException upon failure
   */
  Page<BucketInfo> list(BucketListOption... options);

  /**
   * List the bucket's blobs.
   *
   * @throws StorageException upon failure
   */
  Page<BlobInfo> list(String bucket, BlobListOption... options);

  /**
   * Update bucket information.
   *
   * @return the updated bucket
   * @throws StorageException upon failure
   */
  BucketInfo update(BucketInfo bucketInfo, BucketTargetOption... options);

  /**
   * Update blob information. Original metadata are merged with metadata in the provided
   * {@code blobInfo}. To replace metadata instead you first have to unset them. Unsetting metadata
   * can be done by setting the provided {@code blobInfo}'s metadata to {@code null}.
   * <p>
   * Example usage of replacing blob's metadata:
   * <pre>    {@code service.update(BlobInfo.builder("bucket", "name").metadata(null).build());}
   *    {@code service.update(BlobInfo.builder("bucket", "name").metadata(newMetadata).build());}
   * </pre>
   *
   * @return the updated blob
   * @throws StorageException upon failure
   */
  BlobInfo update(BlobInfo blobInfo, BlobTargetOption... options);

  /**
   * Update blob information. Original metadata are merged with metadata in the provided
   * {@code blobInfo}. To replace metadata instead you first have to unset them. Unsetting metadata
   * can be done by setting the provided {@code blobInfo}'s metadata to {@code null}.
   * <p>
   * Example usage of replacing blob's metadata:
   * <pre>    {@code service.update(BlobInfo.builder("bucket", "name").metadata(null).build());}
   *    {@code service.update(BlobInfo.builder("bucket", "name").metadata(newMetadata).build());}
   * </pre>
   *
   * @return the updated blob
   * @throws StorageException upon failure
   */
  BlobInfo update(BlobInfo blobInfo);

  /**
   * Delete the requested bucket.
   *
   * @return true if bucket was deleted
   * @throws StorageException upon failure
   */
  boolean delete(String bucket, BucketSourceOption... options);

  /**
   * Delete the requested blob.
   *
   * @return true if blob was deleted
   * @throws StorageException upon failure
   */
  boolean delete(String bucket, String blob, BlobSourceOption... options);

  /**
   * Delete the requested blob.
   *
   * @return true if blob was deleted
   * @throws StorageException upon failure
   */
  boolean delete(BlobId blob, BlobSourceOption... options);

  /**
   * Delete the requested blob.
   *
   * @return true if blob was deleted
   * @throws StorageException upon failure
   */
  boolean delete(BlobId blob);

  /**
   * Send a compose request.
   *
   * @return the composed blob.
   * @throws StorageException upon failure
   */
  BlobInfo compose(ComposeRequest composeRequest);

  /**
   * Sends a copy request. Returns a {@link CopyWriter} object for the provided
   * {@code CopyRequest}. If source and destination objects share the same location and storage
   * class the source blob is copied with one request and {@link CopyWriter#result()} immediately
   * returns, regardless of the {@link CopyRequest#megabytesCopiedPerChunk} parameter.
   * If source and destination have different location or storage class {@link CopyWriter#result()}
   * might issue multiple RPC calls depending on blob's size.
   * <p>
   * Example usage of copy:
   * <pre>    {@code BlobInfo blob = service.copy(copyRequest).result();}
   * </pre>
   * To explicitly issue chunk copy requests use {@link CopyWriter#copyChunk()} instead:
   * <pre>    {@code CopyWriter copyWriter = service.copy(copyRequest);
   *    while (!copyWriter.isDone()) {
   *        copyWriter.copyChunk();
   *    }
   *    BlobInfo blob = copyWriter.result();
   * }
   * </pre>
   *
   * @return a {@link CopyWriter} object that can be used to get information on the newly created
   *     blob or to complete the copy if more than one RPC request is needed
   * @throws StorageException upon failure
   * @see <a href="https://cloud.google.com/storage/docs/json_api/v1/objects/rewrite">Rewrite</a>
   */
  CopyWriter copy(CopyRequest copyRequest);

  /**
   * Reads all the bytes from a blob.
   *
   * @return the blob's content.
   * @throws StorageException upon failure
   */
  byte[] readAllBytes(String bucket, String blob, BlobSourceOption... options);

  /**
   * Reads all the bytes from a blob.
   *
   * @return the blob's content.
   * @throws StorageException upon failure
   */
  byte[] readAllBytes(BlobId blob, BlobSourceOption... options);

  /**
   * Send a batch request.
   *
   * @return the batch response
   * @throws StorageException upon failure
   */
  BatchResponse apply(BatchRequest batchRequest);

  /**
   * Return a channel for reading the blob's content.
   *
   * @throws StorageException upon failure
   */
  BlobReadChannel reader(String bucket, String blob, BlobSourceOption... options);

  /**
   * Return a channel for reading the blob's content.
   *
   * @throws StorageException upon failure
   */
  BlobReadChannel reader(BlobId blob, BlobSourceOption... options);

  /**
   * Create a blob and return a channel for writing its content. By default any md5 and crc32c
   * values in the given {@code blobInfo} are ignored unless requested via the
   * {@code BlobWriteOption.md5Match} and {@code BlobWriteOption.crc32cMatch} options.
   *
   * @throws StorageException upon failure
   */
  BlobWriteChannel writer(BlobInfo blobInfo, BlobWriteOption... options);

  /**
   * Generates a signed URL for a blob.
   * If you have a blob that you want to allow access to for a fixed
   * amount of time, you can use this method to generate a URL that
   * is only valid within a certain time period.
   * This is particularly useful if you don't want publicly
   * accessible blobs, but don't want to require users to explicitly log in.
   * <p>
   * Example usage of creating a signed URL that is valid for 2 weeks:
   * <pre>   {@code
   *     service.signUrl(BlobInfo.builder("bucket", "name").build(), 14, TimeUnit.DAYS);
   * }</pre>
   *
   * @param blobInfo the blob associated with the signed URL
   * @param duration time until the signed URL expires, expressed in {@code unit}. The finer
   *     granularity supported is 1 second, finer granularities will be truncated
   * @param unit time unit of the {@code duration} parameter
   * @param options optional URL signing options
   * @see <a href="https://cloud.google.com/storage/docs/access-control#Signed-URLs">Signed-URLs</a>
   */
  URL signUrl(BlobInfo blobInfo, long duration, TimeUnit unit, SignUrlOption... options);

  /**
   * Gets the requested blobs. A batch request is used to perform this call.
   *
   * @param blobIds blobs to get
   * @return an immutable list of {@code BlobInfo} objects. If a blob does not exist or access to it
   *     has been denied the corresponding item in the list is {@code null}.
   * @throws StorageException upon failure
   */
  List<BlobInfo> get(BlobId... blobIds);

  /**
   * Updates the requested blobs. A batch request is used to perform this call. Original metadata
   * are merged with metadata in the provided {@code BlobInfo} objects. To replace metadata instead
   * you first have to unset them. Unsetting metadata can be done by setting the provided
   * {@code BlobInfo} objects metadata to {@code null}. See
   * {@link #update(com.google.gcloud.storage.BlobInfo)} for a code example.
   *
   * @param blobInfos blobs to update
   * @return an immutable list of {@code BlobInfo} objects. If a blob does not exist or access to it
   *     has been denied the corresponding item in the list is {@code null}.
   * @throws StorageException upon failure
   */
  List<BlobInfo> update(BlobInfo... blobInfos);

  /**
   * Deletes the requested blobs. A batch request is used to perform this call.
   *
   * @param blobIds blobs to delete
   * @return an immutable list of booleans. If a blob has been deleted the corresponding item in the
   *     list is {@code true}. If deletion failed or access to the resource was denied the item is
   *     {@code false}.
   * @throws StorageException upon failure
   */
  List<Boolean> delete(BlobId... blobIds);
}
