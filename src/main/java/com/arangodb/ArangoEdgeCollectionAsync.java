/*
 * DISCLAIMER
 *
 * Copyright 2016 ArangoDB GmbH, Cologne, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright holder is ArangoDB GmbH, Cologne, Germany
 */

package com.arangodb;

import java.util.concurrent.CompletableFuture;

import com.arangodb.entity.EdgeEntity;
import com.arangodb.entity.EdgeUpdateEntity;
import com.arangodb.internal.ArangoExecutorAsync;
import com.arangodb.internal.InternalArangoEdgeCollection;
import com.arangodb.internal.velocystream.ConnectionAsync;
import com.arangodb.model.DocumentReadOptions;
import com.arangodb.model.EdgeCreateOptions;
import com.arangodb.model.EdgeDeleteOptions;
import com.arangodb.model.EdgeReplaceOptions;
import com.arangodb.model.EdgeUpdateOptions;
import com.arangodb.velocystream.Response;

/**
 * @author Mark - mark at arangodb.com
 *
 */
public class ArangoEdgeCollectionAsync
		extends InternalArangoEdgeCollection<ArangoExecutorAsync, CompletableFuture<Response>, ConnectionAsync> {

	protected ArangoEdgeCollectionAsync(final ArangoGraphAsync graph, final String name) {
		super(graph.executor(), graph.db(), graph.name(), name);
	}

	/**
	 * Creates a new edge in the collection
	 * 
	 * @see <a href="https://docs.arangodb.com/current/HTTP/Gharial/Edges.html#create-an-edge">API Documentation</a>
	 * @param value
	 *            A representation of a single edge (POJO, VPackSlice or String for Json)
	 * @return information about the edge
	 */
	public <T> CompletableFuture<EdgeEntity> insertEdge(final T value) {
		return executor.execute(insertEdgeRequest(value, new EdgeCreateOptions()),
			insertEdgeResponseDeserializer(value));
	}

	/**
	 * Creates a new edge in the collection
	 * 
	 * @see <a href="https://docs.arangodb.com/current/HTTP/Gharial/Edges.html#create-an-edge">API Documentation</a>
	 * @param value
	 *            A representation of a single edge (POJO, VPackSlice or String for Json)
	 * @param options
	 *            Additional options, can be null
	 * @return information about the edge
	 */
	public <T> CompletableFuture<EdgeEntity> insertEdge(final T value, final EdgeCreateOptions options) {
		return executor.execute(insertEdgeRequest(value, options), insertEdgeResponseDeserializer(value));
	}

	/**
	 * Fetches an existing edge
	 * 
	 * @see <a href="https://docs.arangodb.com/current/HTTP/Gharial/Edges.html#get-an-edge">API Documentation</a>
	 * @param key
	 *            The key of the edge
	 * @param type
	 *            The type of the edge-document (POJO class, VPackSlice or String for Json)
	 * @return the edge identified by the key
	 */
	public <T> CompletableFuture<T> getEdge(final String key, final Class<T> type) {
		return executor.execute(getEdgeRequest(key, new DocumentReadOptions()), getEdgeResponseDeserializer(type));
	}

	/**
	 * Fetches an existing edge
	 * 
	 * @see <a href="https://docs.arangodb.com/current/HTTP/Gharial/Edges.html#get-an-edge">API Documentation</a>
	 * @param key
	 *            The key of the edge
	 * @param type
	 *            The type of the edge-document (POJO class, VPackSlice or String for Json)
	 * @param options
	 *            Additional options, can be null
	 * @return the edge identified by the key
	 */
	public <T> CompletableFuture<T> getEdge(final String key, final Class<T> type, final DocumentReadOptions options) {
		return executor.execute(getEdgeRequest(key, options), getEdgeResponseDeserializer(type));
	}

	/**
	 * Replaces the edge with key with the one in the body, provided there is such a edge and no precondition is
	 * violated
	 * 
	 * @see <a href="https://docs.arangodb.com/current/HTTP/Gharial/Edges.html#replace-an-edge">API Documentation</a>
	 * @param key
	 *            The key of the edge
	 * @param type
	 *            The type of the edge-document (POJO class, VPackSlice or String for Json)
	 * @return information about the edge
	 */
	public <T> CompletableFuture<EdgeUpdateEntity> replaceEdge(final String key, final T value) {
		return executor.execute(replaceEdgeRequest(key, value, new EdgeReplaceOptions()),
			replaceEdgeResponseDeserializer(value));
	}

	/**
	 * Replaces the edge with key with the one in the body, provided there is such a edge and no precondition is
	 * violated
	 * 
	 * @see <a href="https://docs.arangodb.com/current/HTTP/Gharial/Edges.html#replace-an-edge">API Documentation</a>
	 * @param key
	 *            The key of the edge
	 * @param type
	 *            The type of the edge-document (POJO class, VPackSlice or String for Json)
	 * @param options
	 *            Additional options, can be null
	 * @return information about the edge
	 */
	public <T> CompletableFuture<EdgeUpdateEntity> replaceEdge(
		final String key,
		final T value,
		final EdgeReplaceOptions options) {
		return executor.execute(replaceEdgeRequest(key, value, options), replaceEdgeResponseDeserializer(value));
	}

	/**
	 * Partially updates the edge identified by document-key. The value must contain a document with the attributes to
	 * patch (the patch document). All attributes from the patch document will be added to the existing document if they
	 * do not yet exist, and overwritten in the existing document if they do exist there.
	 * 
	 * @see <a href="https://docs.arangodb.com/current/HTTP/Gharial/Edges.html#modify-an-edge">API Documentation</a>
	 * @param key
	 *            The key of the edge
	 * @param type
	 *            The type of the edge-document (POJO class, VPackSlice or String for Json)
	 * @return information about the edge
	 */
	public <T> CompletableFuture<EdgeUpdateEntity> updateEdge(final String key, final T value) {
		return executor.execute(updateEdgeRequest(key, value, new EdgeUpdateOptions()),
			updateEdgeResponseDeserializer(value));
	}

	/**
	 * Partially updates the edge identified by document-key. The value must contain a document with the attributes to
	 * patch (the patch document). All attributes from the patch document will be added to the existing document if they
	 * do not yet exist, and overwritten in the existing document if they do exist there.
	 * 
	 * @see <a href="https://docs.arangodb.com/current/HTTP/Gharial/Edges.html#modify-an-edge">API Documentation</a>
	 * @param key
	 *            The key of the edge
	 * @param type
	 *            The type of the edge-document (POJO class, VPackSlice or String for Json)
	 * @param options
	 *            Additional options, can be null
	 * @return information about the edge
	 */
	public <T> CompletableFuture<EdgeUpdateEntity> updateEdge(
		final String key,
		final T value,
		final EdgeUpdateOptions options) {
		return executor.execute(updateEdgeRequest(key, value, options), updateEdgeResponseDeserializer(value));
	}

	/**
	 * Removes a edge
	 * 
	 * @see <a href="https://docs.arangodb.com/current/HTTP/Gharial/Edges.html#remove-an-edge">API Documentation</a>
	 * @param key
	 *            The key of the edge
	 */
	public CompletableFuture<Void> deleteEdge(final String key) {
		return executor.execute(deleteEdgeRequest(key, new EdgeDeleteOptions()), Void.class);
	}

	/**
	 * Removes a edge
	 * 
	 * @see <a href="https://docs.arangodb.com/current/HTTP/Gharial/Edges.html#remove-an-edge">API Documentation</a>
	 * @param key
	 *            The key of the edge
	 * @param options
	 *            Additional options, can be null
	 */
	public CompletableFuture<Void> deleteEdge(final String key, final EdgeDeleteOptions options) {
		return executor.execute(deleteEdgeRequest(key, options), Void.class);
	}

}
