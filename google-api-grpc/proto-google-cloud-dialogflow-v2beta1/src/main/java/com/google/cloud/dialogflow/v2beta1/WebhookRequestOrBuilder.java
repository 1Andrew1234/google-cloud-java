// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/cloud/dialogflow/v2beta1/webhook.proto

package com.google.cloud.dialogflow.v2beta1;

public interface WebhookRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.cloud.dialogflow.v2beta1.WebhookRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * The unique identifier of detectIntent request session.
   * Can be used to identify end-user inside webhook implementation.
   * Format: `projects/&lt;Project ID&gt;/agent/sessions/&lt;Session ID&gt;`.
   * </pre>
   *
   * <code>string session = 4;</code>
   */
  java.lang.String getSession();
  /**
   * <pre>
   * The unique identifier of detectIntent request session.
   * Can be used to identify end-user inside webhook implementation.
   * Format: `projects/&lt;Project ID&gt;/agent/sessions/&lt;Session ID&gt;`.
   * </pre>
   *
   * <code>string session = 4;</code>
   */
  com.google.protobuf.ByteString
      getSessionBytes();

  /**
   * <pre>
   * The unique identifier of the response. Contains the same value as
   * `[Streaming]DetectIntentResponse.response_id`.
   * </pre>
   *
   * <code>string response_id = 1;</code>
   */
  java.lang.String getResponseId();
  /**
   * <pre>
   * The unique identifier of the response. Contains the same value as
   * `[Streaming]DetectIntentResponse.response_id`.
   * </pre>
   *
   * <code>string response_id = 1;</code>
   */
  com.google.protobuf.ByteString
      getResponseIdBytes();

  /**
   * <pre>
   * The result of the conversational query or event processing. Contains the
   * same value as `[Streaming]DetectIntentResponse.query_result`.
   * </pre>
   *
   * <code>.google.cloud.dialogflow.v2beta1.QueryResult query_result = 2;</code>
   */
  boolean hasQueryResult();
  /**
   * <pre>
   * The result of the conversational query or event processing. Contains the
   * same value as `[Streaming]DetectIntentResponse.query_result`.
   * </pre>
   *
   * <code>.google.cloud.dialogflow.v2beta1.QueryResult query_result = 2;</code>
   */
  com.google.cloud.dialogflow.v2beta1.QueryResult getQueryResult();
  /**
   * <pre>
   * The result of the conversational query or event processing. Contains the
   * same value as `[Streaming]DetectIntentResponse.query_result`.
   * </pre>
   *
   * <code>.google.cloud.dialogflow.v2beta1.QueryResult query_result = 2;</code>
   */
  com.google.cloud.dialogflow.v2beta1.QueryResultOrBuilder getQueryResultOrBuilder();

  /**
   * <pre>
   * Optional. The contents of the original request that was passed to
   * `[Streaming]DetectIntent` call.
   * </pre>
   *
   * <code>.google.cloud.dialogflow.v2beta1.OriginalDetectIntentRequest original_detect_intent_request = 3;</code>
   */
  boolean hasOriginalDetectIntentRequest();
  /**
   * <pre>
   * Optional. The contents of the original request that was passed to
   * `[Streaming]DetectIntent` call.
   * </pre>
   *
   * <code>.google.cloud.dialogflow.v2beta1.OriginalDetectIntentRequest original_detect_intent_request = 3;</code>
   */
  com.google.cloud.dialogflow.v2beta1.OriginalDetectIntentRequest getOriginalDetectIntentRequest();
  /**
   * <pre>
   * Optional. The contents of the original request that was passed to
   * `[Streaming]DetectIntent` call.
   * </pre>
   *
   * <code>.google.cloud.dialogflow.v2beta1.OriginalDetectIntentRequest original_detect_intent_request = 3;</code>
   */
  com.google.cloud.dialogflow.v2beta1.OriginalDetectIntentRequestOrBuilder getOriginalDetectIntentRequestOrBuilder();
}
