/*
 *
 */
package io.github.pflima92.plyshare.gateway.library;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.jspare.core.annotation.Inject;
import org.jspare.core.annotation.Resource;

import io.github.pflima92.plyshare.gateway.GatewayOptionsHolder;
import lombok.SneakyThrows;

@Resource
public class LibrarySupport {

	@Inject
	private GatewayOptionsHolder gatewayOptionsHolder;

	public void load() {

		listFiles().forEach(this::addSoftwareLibrary);
	}

	@SneakyThrows
	private void addSoftwareLibrary(File file) {
		Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
		method.setAccessible(true);
		method.invoke(ClassLoader.getSystemClassLoader(), new Object[] { file.toURI().toURL() });
	}

	private List<File> listFiles() {

		File file = new File(gatewayOptionsHolder.getOptions().getLibPath());
		if (file.isDirectory()) {

			return Arrays.asList(file.listFiles()).stream().filter(f -> f.getName().endsWith("jar")).collect(Collectors.toList());
		}
		return Arrays.asList();
	}
}