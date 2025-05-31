package de.craftsblock.craftsnet.intellijplugin.inspection.builtin

import de.craftsblock.craftsnet.intellijplugin.inspection.CustomAnnotatedInspection
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.DynamicUrlParamInspectionRule
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.ParameterInspectionRule
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.RequireImplementationInspectionRule
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.TransformerInspectionRule

class SocketInspection : CustomAnnotatedInspection(
    "Socket",
    "de.craftsblock.craftsnet.api.websocket.annotations.Socket",
    DynamicUrlParamInspectionRule(2),
    ParameterInspectionRule(0, "exchange", "de.craftsblock.craftsnet.api.websocket.SocketExchange"),
    ParameterInspectionRule(
        1,
        "message",
        "java.lang.String",
        "de.craftsblock.craftsnet.api.websocket.Frame",
        "de.craftsblock.craftsnet.utils.ByteBuffer",
        "byte[]", "java.lang.Byte[]"
    ),
    RequireImplementationInspectionRule("de.craftsblock.craftsnet.api.websocket.SocketHandler"),
    TransformerInspectionRule(2),
)