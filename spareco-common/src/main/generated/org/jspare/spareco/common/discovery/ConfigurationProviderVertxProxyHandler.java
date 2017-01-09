/*
 *
 */

package org.jspare.spareco.common.discovery;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHandler;
import io.vertx.serviceproxy.ServiceException;
import io.vertx.serviceproxy.ServiceExceptionMessageCodec;

/*
  Generated Proxy code - DO NOT EDIT
  @author Roger the Robot
*/
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ConfigurationProviderVertxProxyHandler extends ProxyHandler {

	public static final long DEFAULT_CONNECTION_TIMEOUT = 5 * 60; // 5 minutes

	private final Vertx vertx;
	private final ConfigurationProvider service;
	private final long timerID;
	private long lastAccessed;
	private final long timeoutSeconds;

	public ConfigurationProviderVertxProxyHandler(Vertx vertx, ConfigurationProvider service) {
		this(vertx, service, DEFAULT_CONNECTION_TIMEOUT);
	}

	public ConfigurationProviderVertxProxyHandler(Vertx vertx, ConfigurationProvider service, boolean topLevel, long timeoutSeconds) {
		this.vertx = vertx;
		this.service = service;
		this.timeoutSeconds = timeoutSeconds;
		try {
			this.vertx.eventBus().registerDefaultCodec(ServiceException.class, new ServiceExceptionMessageCodec());
		} catch (IllegalStateException ex) {
		}
		if (timeoutSeconds != -1 && !topLevel) {
			long period = timeoutSeconds * 1000 / 2;
			if (period > 10000) {
				period = 10000;
			}
			timerID = vertx.setPeriodic(period, this::checkTimedOut);
		} else {
			timerID = -1;
		}
		accessed();
	}

	public ConfigurationProviderVertxProxyHandler(Vertx vertx, ConfigurationProvider service, long timeoutInSecond) {
		this(vertx, service, true, timeoutInSecond);
	}

	@Override
	public void close() {
		if (timerID != -1) {
			vertx.cancelTimer(timerID);
		}
		super.close();
	}

	@Override
	public void handle(Message<JsonObject> msg) {
		try {
			JsonObject json = msg.body();
			String action = msg.headers().get("action");
			if (action == null) {
				throw new IllegalStateException("action not specified");
			}
			accessed();
			switch (action) {
			case "getConfiguration": {
				service.getConfiguration((java.lang.String) json.getValue("alias"), createHandler(msg));
				break;
			}
			default: {
				throw new IllegalStateException("Invalid action: " + action);
			}
			}
		} catch (Throwable t) {
			msg.reply(new ServiceException(500, t.getMessage()));
			throw t;
		}
	}

	@Override
	public MessageConsumer<JsonObject> registerHandler(String address) {
		MessageConsumer<JsonObject> consumer = vertx.eventBus().<JsonObject>consumer(address).handler(this);
		setConsumer(consumer);
		return consumer;
	}

	private void accessed() {
		lastAccessed = System.nanoTime();
	}

	private void checkTimedOut(long id) {
		long now = System.nanoTime();
		if (now - lastAccessed > timeoutSeconds * 1000000000) {
			close();
		}
	}

	private <T> List<T> convertList(List list) {
		return list;
	}

	private <T> Map<String, T> convertMap(Map map) {
		return map;
	}

	private <T> Set<T> convertSet(List list) {
		return new HashSet<T>(list);
	}

	private <T> Handler<AsyncResult<T>> createHandler(Message msg) {
		return res -> {
			if (res.failed()) {
				if (res.cause() instanceof ServiceException) {
					msg.reply(res.cause());
				} else {
					msg.reply(new ServiceException(-1, res.cause().getMessage()));
				}
			} else {
				if (res.result() != null && res.result().getClass().isEnum()) {
					msg.reply(((Enum) res.result()).name());
				} else {
					msg.reply(res.result());
				}
			}
		};
	}

	private Handler<AsyncResult<List<Character>>> createListCharHandler(Message msg) {
		return res -> {
			if (res.failed()) {
				if (res.cause() instanceof ServiceException) {
					msg.reply(res.cause());
				} else {
					msg.reply(new ServiceException(-1, res.cause().getMessage()));
				}
			} else {
				JsonArray arr = new JsonArray();
				for (Character chr : res.result()) {
					arr.add((int) chr);
				}
				msg.reply(arr);
			}
		};
	}

	private <T> Handler<AsyncResult<List<T>>> createListHandler(Message msg) {
		return res -> {
			if (res.failed()) {
				if (res.cause() instanceof ServiceException) {
					msg.reply(res.cause());
				} else {
					msg.reply(new ServiceException(-1, res.cause().getMessage()));
				}
			} else {
				msg.reply(new JsonArray(res.result()));
			}
		};
	}

	private Handler<AsyncResult<Set<Character>>> createSetCharHandler(Message msg) {
		return res -> {
			if (res.failed()) {
				if (res.cause() instanceof ServiceException) {
					msg.reply(res.cause());
				} else {
					msg.reply(new ServiceException(-1, res.cause().getMessage()));
				}
			} else {
				JsonArray arr = new JsonArray();
				for (Character chr : res.result()) {
					arr.add((int) chr);
				}
				msg.reply(arr);
			}
		};
	}

	private <T> Handler<AsyncResult<Set<T>>> createSetHandler(Message msg) {
		return res -> {
			if (res.failed()) {
				if (res.cause() instanceof ServiceException) {
					msg.reply(res.cause());
				} else {
					msg.reply(new ServiceException(-1, res.cause().getMessage()));
				}
			} else {
				msg.reply(new JsonArray(new ArrayList<>(res.result())));
			}
		};
	}
}