package de.craftsblock.craftsnet.intellijplugin.templates.impl

import com.intellij.icons.AllIcons
import de.craftsblock.craftsnet.intellijplugin.templates.CraftsNetTemplates
import de.craftsblock.craftsnet.intellijplugin.templates.CustomDialogTemplateAction
import de.craftsblock.craftsnet.intellijplugin.templates.TemplateKind
import de.craftsblock.craftsnet.intellijplugin.uitls.versioning.FeatureFlag

class CodecTemplates : CustomDialogTemplateAction(
    "Codec",
    "",
    AllIcons.FileTypes.Java,
    TemplateKind("Encoder", AllIcons.FileTypes.Java, CraftsNetTemplates.ENCODER),
    TemplateKind("Decoder", AllIcons.FileTypes.Java, CraftsNetTemplates.DECODER),
    TemplateKind("WebSocketTypeDecoder", AllIcons.FileTypes.Java, CraftsNetTemplates.WEB_SOCKET_SAFE_TYPE_DECODER),
    TemplateKind(
        "WebSocketToByteArrayEncoder", AllIcons.FileTypes.Java, CraftsNetTemplates.WEB_SOCKET_SAFE_TYPE_ENCODER,
        args = mapOf(
            Pair("ENCODER_NAME", "ByteArray"),
            Pair("ENCODER_TYPE", "byte[]")
        )
    ),
    TemplateKind(
        "WebSocketToByteBufferEncoder", AllIcons.FileTypes.Java, CraftsNetTemplates.WEB_SOCKET_SAFE_TYPE_ENCODER,
        args = mapOf(
            Pair("ENCODER_NAME", "ByteBuffer"),
            Pair("ENCODER_IMPORT", "de.craftsblock.craftsnet.utils.ByteBuffer"),
        )
    ),
    TemplateKind(
        "WebSocketToByteStringEncoder", AllIcons.FileTypes.Java, CraftsNetTemplates.WEB_SOCKET_SAFE_TYPE_ENCODER,
        args = mapOf(
            Pair("ENCODER_NAME", "String"),
        )
    ),
    TemplateKind(
        "WebSocketToByteJsonEncoder", AllIcons.FileTypes.Java, CraftsNetTemplates.WEB_SOCKET_SAFE_TYPE_ENCODER,
        args = mapOf(
            Pair("ENCODER_NAME", "Json"),
            Pair("ENCODER_IMPORT", "de.craftsblock.craftscore.json.Json"),
        )
    ),
    featureFlag = FeatureFlag.BASE_350
)