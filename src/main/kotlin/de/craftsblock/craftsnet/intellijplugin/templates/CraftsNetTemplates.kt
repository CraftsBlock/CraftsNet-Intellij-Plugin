package de.craftsblock.craftsnet.intellijplugin.templates

import com.intellij.icons.AllIcons
import com.intellij.ide.fileTemplates.FileTemplateDescriptor
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptor
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptorFactory

class CraftsNetTemplates : FileTemplateGroupDescriptorFactory {

    override fun getFileTemplatesDescriptor(): FileTemplateGroupDescriptor {
        val group = FileTemplateGroupDescriptor("CraftsNet", AllIcons.FileTypes.Java)

        FileTemplateGroupDescriptor("Endpoints", AllIcons.FileTypes.Java).let { routesGroup ->
            group.addTemplate(routesGroup)

            routesGroup.addTemplate(FileTemplateDescriptor(ROUTE))
            routesGroup.addTemplate(FileTemplateDescriptor(SOCKET))
        }

        FileTemplateGroupDescriptor("Addon", AllIcons.FileTypes.Java).let { addonGroup ->
            group.addTemplate(addonGroup)

            addonGroup.addTemplate(FileTemplateDescriptor(ADDON))
            addonGroup.addTemplate(FileTemplateDescriptor(ADDON_JSON))
            addonGroup.addTemplate(FileTemplateDescriptor(SERVICE_LOADER))
        }

        FileTemplateGroupDescriptor("Auto Register", AllIcons.FileTypes.Java).let { autoRegisterGroup ->
            group.addTemplate(autoRegisterGroup)

            autoRegisterGroup.addTemplate(FileTemplateDescriptor(AUTO_REGISTER_HANDLER))
        }

        FileTemplateGroupDescriptor("HTTP", AllIcons.FileTypes.Java).let { httpGroup ->
            group.addTemplate(httpGroup)

            httpGroup.addTemplate(FileTemplateDescriptor(BODY))
            httpGroup.addTemplate(FileTemplateDescriptor(BODY_PARSER))
            httpGroup.addTemplate(FileTemplateDescriptor(SESSION_DRIVER))
            httpGroup.addTemplate(FileTemplateDescriptor(STREAM_ENCODER))
            httpGroup.addTemplate(FileTemplateDescriptor(TRANSFORMER))
        }

        FileTemplateGroupDescriptor("Middlewares", AllIcons.FileTypes.Java).let { middlewareGroup ->
            group.addTemplate(middlewareGroup)

            middlewareGroup.addTemplate(FileTemplateDescriptor(MIDDLEWARE))
            middlewareGroup.addTemplate(FileTemplateDescriptor(HTTP_MIDDLEWARE))
            middlewareGroup.addTemplate(FileTemplateDescriptor(WEBSOCKET_MIDDLEWARE))
        }

        FileTemplateGroupDescriptor("Websocket", AllIcons.FileTypes.Java).let { websocketGroup ->
            group.addTemplate(websocketGroup)

            websocketGroup.addTemplate(FileTemplateDescriptor(WEBSOCKET_EXTENSION))
        }

        FileTemplateGroupDescriptor("Requirements", AllIcons.FileTypes.Java).let { requirementsGroup ->
            group.addTemplate(requirementsGroup)

            requirementsGroup.addTemplate(FileTemplateDescriptor(WEB_REQUIREMENT))
            requirementsGroup.addTemplate(FileTemplateDescriptor(WEBSOCKET_REQUIREMENT))
        }

        FileTemplateGroupDescriptor("Events", AllIcons.FileTypes.Java).let { eventsGroup ->
            group.addTemplate(eventsGroup)

            eventsGroup.addTemplate(FileTemplateDescriptor(LISTENER))
            eventsGroup.addTemplate(FileTemplateDescriptor(EVENT))
            eventsGroup.addTemplate(FileTemplateDescriptor(CANCELLABLE_EVENT))
        }

        FileTemplateGroupDescriptor("CLI", AllIcons.FileTypes.Java).let { cliGroup ->
            group.addTemplate(cliGroup)

            cliGroup.addTemplate(FileTemplateDescriptor(COMMAND_EXECUTOR))
        }

        group.addTemplate(FileTemplateDescriptor(LOGGER))

        return group
    }

    companion object {

        // Endpoints
        const val ROUTE = "Route.java"
        const val SOCKET = "Socket.java"

        // ADDON
        const val ADDON = "Addon.java"
        const val ADDON_JSON = "addon.json"
        const val SERVICE_LOADER = "ServiceLoader.java"

        // Auto register
        const val AUTO_REGISTER_HANDLER = "AutoRegisterHandler.java"

        // HTTP
        const val BODY = "Body.java"
        const val BODY_PARSER = "BodyParser.java"
        const val SESSION_DRIVER = "SessionDriver.java"
        const val STREAM_ENCODER = "StreamEncoder.java"
        const val TRANSFORMER = "Transformer.java"

        // Middlewares
        const val MIDDLEWARE = "Middleware.java"
        const val HTTP_MIDDLEWARE = "HttpMiddleware.java"
        const val WEBSOCKET_MIDDLEWARE = "WebsocketMiddleware.java"

        // Websocket
        const val WEBSOCKET_EXTENSION = "WebSocketExtension.java"

        // Requirements
        const val WEB_REQUIREMENT = "WebRequirement.java"
        const val WEBSOCKET_REQUIREMENT = "WebSocketRequirement.java"

        // Events
        const val LISTENER = "Listener.java"
        const val EVENT = "Event.java"
        const val CANCELLABLE_EVENT = "CancellableEvent.java"

        // CLI
        const val COMMAND_EXECUTOR = "CommandExecutor.java"

        // Logging
        const val LOGGER = "Logger.java"

    }

}