# Earthport Webhook Signature Decoder

This sample project contains Java code which shows examples of how to decode an Earthport webhook notification signature.

## What is an Earthport Webhook Signature? 

When Earthport sends webhook notifications to client endpoints, we include a set of HTTP HEADERS which you can choose to use to help you verify the webhook is valid and was actually sent by Earthport.
These can help prevent man in the middle attacks.

## HTTP HEADERS

### client-id
The client-id is used during authentication with the REST APIs.

e.g.
y9OJrEnfV6ZHFospsQSeSiJqhfaUD6lH

### origin
 The Earthport environment which generated the notification. This is useful when troubleshooting unexpected notifications. Notification are either generated from Production or Sandbox.
 
 e.g.
 api-integration.earthport.com
 
 ### earthport-signature
 Signature which can be used to verify the notification is valid and from Earthport.
 
 e.g.
 1544701184264.V38kC60MSXVqZtcNLMr0upPvv0gBPEKlIVfLB0wB1kI=
 
 
