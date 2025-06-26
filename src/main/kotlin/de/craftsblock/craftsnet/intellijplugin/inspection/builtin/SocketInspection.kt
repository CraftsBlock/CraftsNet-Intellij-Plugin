package de.craftsblock.craftsnet.intellijplugin.inspection.builtin

import de.craftsblock.craftsnet.intellijplugin.inspection.CustomAnnotatedInspection
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.FeatureFlagSpecificInspectionRule
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.craftsnet.DynamicUrlParamInspectionRule
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.craftsnet.ParameterInspectionRule
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.craftsnet.TransformerInspectionRule
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.direct.MethodReturnTypeInspectionRule
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.direct.RequireImplementationInspectionRule
import de.craftsblock.craftsnet.intellijplugin.uitls.versioning.FeatureFlag

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
    FeatureFlagSpecificInspectionRule(
        MethodReturnTypeInspectionRule("*"),
        MethodReturnTypeInspectionRule("void"),
        FeatureFlag.PRINTING_RETURN_VALUES
    ),
)