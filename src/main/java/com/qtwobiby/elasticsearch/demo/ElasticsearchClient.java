package com.qtwobiby.elasticsearch.demo;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.admin.cluster.snapshots.status.TransportNodesSnapshotsStatus;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElasticsearchClient {
private static Logger LOGGER = LoggerFactory.getLogger(ElasticsearchClient.class);
	private TransportClient client;

	public ElasticsearchClient() {}

	private void setTransportClient(TransportClient client) {
		this.client = client;
	}

	public TransportClient getTransportClient() {
		return this.client;
	}

	public static ElasticsearchClientBuilder builder() {
		return new ElasticsearchClientBuilder();
	}

	public boolean isExistIndex(String index) throws ExecutionException, InterruptedException {
		LOGGER.info("IS EXIST INDEX arguments, index: {} ", index);
		IndicesExistsRequest request = Requests.indicesExistsRequest(index);
		IndicesExistsResponse response = client.admin().indices().exists(request).get();
		boolean isExists = response.isExists();
		LOGGER.info("IS EXIST INDEX response, {} is isExists: {}", index, isExists);
		return isExists;
	}

	public String[] getIndices() throws ExecutionException, InterruptedException {
		GetIndexRequest request = new GetIndexRequest();
		GetIndexResponse response = client.admin().indices().getIndex(request).get();
		String[] indices = response.indices();
		return indices;
	}

	public boolean createIndex(String index) {
		int numberOfShards = 4;
		int numberOfReplicas = 1;
		String refreshInterval = "10s";
		return createIndex(index, numberOfShards, numberOfReplicas, refreshInterval);
	}

	private boolean createIndex(String index, int numberOfShards, int numberOfReplicas, String refreshInterval) {
		LOGGER.info("CREATE INDEX arguments, index: {}", index);
		Settings settings = Settings.builder()
				.put("number_of_shards", numberOfShards)
				.put("number_of_replicas", numberOfReplicas)
				.put("refresh_interval", refreshInterval)
				.build();

		CreateIndexRequestBuilder builder = client.admin().indices().prepareCreate(index);
		builder.setSettings(settings);

		CreateIndexResponse response = builder.get();
		boolean acknowledged = response.isAcknowledged();
		LOGGER.info("CREATE INDEX response detail, index: {}, response: {}, acknowledged: {}", index, response, acknowledged);
		return acknowledged;
	}

	public boolean isExistsType(String index, String type) throws ExecutionException, InterruptedException {
		LOGGER.info("IS EXISTS TYPE arguments, index: {}, type: {}", index, type);
		TypesExistsRequest request = new TypesExistsRequest();
		request.indices(index).types(new String[] { type});
		TypesExistsResponse response = client.admin().indices().typesExists(request).get();
		boolean isExists = response.isExists();
		LOGGER.info("IS EXISTS TYPE response, index: {}, type: {}, response: {}, isExists: {}", index, type, response, isExists);
		return isExists;
	}


	public static class ElasticsearchClientBuilder {
		private Builder settingBuilder = null;
		private Set<InetSocketTransportAddress> transportAddressSet = new HashSet<InetSocketTransportAddress>();
		
		public ElasticsearchClientBuilder() {
			settingBuilder = Settings.builder();
		}
		
		public ElasticsearchClientBuilder sniff() {
			settingBuilder.put("client.transport.sniff", true);
			return this;
		}
		
		public ElasticsearchClientBuilder withClusterName(String clusterName) {
			settingBuilder.put("cluster.name", clusterName);
			return this;
		}
		
		public ElasticsearchClientBuilder ignoreClusterName() {
			settingBuilder.put("client,transport.ignore_cluster_name", true);
			return this;
		}

		public ElasticsearchClientBuilder pingTimeout(int timeout) {
			settingBuilder.put("client.transport.ping_timeout", timeout);
			return this;
		}

		public ElasticsearchClientBuilder nodesSamplerInterval(int interval) {
			settingBuilder.put("client.transport.nodes_sampler_interval", interval);
			return this;
		}

		public ElasticsearchClientBuilder addTransportAddress(String address, int port) {
			InetSocketAddress socketAddress = new InetSocketAddress(address, port);
			InetSocketTransportAddress transportAddress = new InetSocketTransportAddress(socketAddress);
			transportAddressSet.add(transportAddress);
			return this;
		}

		public ElasticsearchClient build() {
			ElasticsearchClient client = new ElasticsearchClient();
			TransportClient transportClient = new PreBuiltTransportClient(settingBuilder.build());
			
			if (transportAddressSet.isEmpty()) {
				transportClient.close();
				throw new IllegalArgumentException("tansport address can't be empty.");
			}

			for (InetSocketTransportAddress address :
					transportAddressSet) {
				transportClient.addTransportAddress(address);
			}
			client.setTransportClient(transportClient);
			return client;
		}
	}

}
