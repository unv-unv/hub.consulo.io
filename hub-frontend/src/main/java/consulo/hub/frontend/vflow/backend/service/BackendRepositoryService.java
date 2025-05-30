package consulo.hub.frontend.vflow.backend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import consulo.hub.shared.repository.FrontPluginNode;
import consulo.hub.shared.repository.PluginChannel;
import consulo.procoeton.core.backend.ApiBackendRequestor;
import consulo.procoeton.core.backend.BackendApiUrl;
import consulo.procoeton.core.backend.BackendServiceDownException;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Consumer;

/**
 * @author VISTALL
 * @since 2021-08-21
 */
@Service
public class BackendRepositoryService {
    private static final Logger LOG = LoggerFactory.getLogger(BackendRepositoryService.class);

    @Autowired
    private ApiBackendRequestor myApiBackendRequestor;

    public void cleanup() {
        try {
            myApiBackendRequestor.runRequest(
                BackendApiUrl.toPrivate("/repository/cleanup"),
                Map.of(),
                new TypeReference<Map<String, String>>() {
                }
            );
        }
        catch (BackendServiceDownException e) {
            throw e;
        }
        catch (Exception e) {
            LOG.error("Fail to get plugins", e);
        }
    }

    public void listAll(@Nonnull Consumer<FrontPluginNode> consumer) {
        try {
            FrontPluginNode[] nodes =
                myApiBackendRequestor.runRequest(BackendApiUrl.toPrivate("/repository/list"), Map.of(), FrontPluginNode[].class);
            if (nodes == null) {
                nodes = new FrontPluginNode[0];
            }

            for (FrontPluginNode node : nodes) {
                consumer.accept(node);
            }
        }
        catch (BackendServiceDownException e) {
            throw e;
        }
        catch (Exception e) {
            LOG.error("Fail to get plugins", e);
        }
    }

    public void iteratePlugins(@Nonnull PluginChannel from, @Nonnull PluginChannel to) {
        try {
            myApiBackendRequestor.runRequest(
                BackendApiUrl.toPrivate("/repository/iterate"),
                Map.of("from", from.name(), "to", to.name()),
                new TypeReference<Map<String, String>>() {
                }
            );
        }
        catch (BackendServiceDownException e) {
            throw e;
        }
        catch (Exception e) {
            LOG.error("Fail iterate plugins, from=" + from + " to=" + to, e);
        }
    }
}
