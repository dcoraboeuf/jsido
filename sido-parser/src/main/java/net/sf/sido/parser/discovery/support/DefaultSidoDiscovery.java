package net.sf.sido.parser.discovery.support;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import net.sf.sido.parser.NamedInput;
import net.sf.sido.parser.SidoParser;
import net.sf.sido.parser.SidoParserFactory;
import net.sf.sido.parser.discovery.SidoDiscovery;
import net.sf.sido.parser.discovery.SidoSchemaDiscovery;
import net.sf.sido.schema.SidoContext;

public class DefaultSidoDiscovery implements SidoDiscovery {

	private static final String COMMENT_PREFIX = "#";

	private static final String SIDO_ENCODING = "UTF-8";

	private static final String SIDO_SCHEMAS = "META-INF/sido/sido.schemas";

	private static final String SIDO_SCHEMA_PATH = "META-INF/sido/%s";

	private final Logger logger = LoggerFactory.getLogger(SidoDiscovery.class);

	@Override
	public Collection<SidoSchemaDiscovery> discover(SidoContext context) {
		// Creates a parser
		SidoParser parser = SidoParserFactory.createParser(context);
		// All results
		Map<String, SidoSchemaDiscovery> discoveries = new HashMap<String, SidoSchemaDiscovery>();
		// Gets all sido.schemas definitions
		try {
			Enumeration<URL> sidoPojoURLs = getClassLoader().getResources(
					SIDO_SCHEMAS);
			while (sidoPojoURLs.hasMoreElements()) {
				URL sidoPojoURL = sidoPojoURLs.nextElement();
				logger.debug("Loading POJO schema definitions from {}",
						sidoPojoURL);
				InputStream sidoSchemas = sidoPojoURL.openStream();
				try {
					// Reads as lines of text
					List<String> lines = IOUtils.readLines(sidoSchemas,
							SIDO_ENCODING);
					// Reads each line, ignoring empty lines and comments
					for (String line : lines) {
						if (StringUtils.isNotBlank(line)) {
							line = StringUtils.trim(line);
							if (!StringUtils.startsWith(line, COMMENT_PREFIX)) {
								logger.debug(
										"Parsing schema discovery definition from {}",
										line);
								SidoSchemaDiscovery discovery = parseDiscovery(line);
								discoveries.put(discovery.getUid(), discovery);
								logger.debug(
										"Parsed schema discovery definition is {}",
										discovery);
							}
						}
					}
				} finally {
					sidoSchemas.close();
				}
			}
		} catch (IOException ex) {
			throw new SidoDiscoveryException(ex);
		}
		// Loading of schema contents
		Collection<NamedInput> inputs = Collections2.transform(discoveries.values(), new Function<SidoSchemaDiscovery, NamedInput>() {

			@Override
			public NamedInput apply(SidoSchemaDiscovery discovery) {
				return loadSchemaContent(discovery);
			}
		});
		// Parsing
		parser.parse(inputs);
		// OK
		return discoveries.values();
	}

	protected NamedInput loadSchemaContent(SidoSchemaDiscovery discovery) {
		// UID
		String uid = discovery.getUid();
		logger.debug("Loading schema content for {}", uid);
		// Resource path
		String schemaPath = String.format(SIDO_SCHEMA_PATH, uid);
		logger.debug("Loading schema content from {}", schemaPath);
		// Gets the resource
		InputStream in = getClassLoader().getResourceAsStream(schemaPath);
		if (in == null) {
			throw new SidoDiscoverySchemaNotFoundException(schemaPath);
		} else {
			try {
				try {
					String content = IOUtils.toString(in, SIDO_ENCODING);
					return new NamedInput(schemaPath, content);
				} finally {
					in.close();
				}
			} catch (IOException ex) {
				throw new SidoDiscoverySchemaIOException(schemaPath, ex);
			}
		}
	}

	protected SidoSchemaDiscovery parseDiscovery(String line) {
		// TODO Parses meta information (model...)
		return new DefaultSidoSchemaDiscovery(line);
	}

	protected ClassLoader getClassLoader() {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		return cl != null ? cl : getClass().getClassLoader();
	}

}
