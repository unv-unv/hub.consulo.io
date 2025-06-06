package consulo.hub.backend.repository;

import consulo.container.internal.plugin.PluginDescriptorImpl;
import consulo.hub.shared.repository.PluginNode;
import jakarta.annotation.Nonnull;

import java.io.File;

/**
 * @author VISTALL
 * @since 05/05/2023
 */
public interface PluginAnalyzerService
{
	@Nonnull
	PluginNode.ExtensionPreview[] analyze(File deployHome, PluginDescriptorImpl descriptor, RepositoryChannelStore channelService) throws Exception;
}
