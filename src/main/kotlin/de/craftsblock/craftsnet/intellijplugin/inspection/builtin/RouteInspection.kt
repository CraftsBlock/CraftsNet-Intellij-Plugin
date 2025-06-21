package de.craftsblock.craftsnet.intellijplugin.inspection.builtin

import de.craftsblock.craftsnet.intellijplugin.inspection.CustomAnnotatedInspection
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.craftsnet.DynamicUrlParamInspectionRule
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.craftsnet.ParameterInspectionRule
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.craftsnet.TransformerInspectionRule
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.direct.RequireImplementationInspectionRule

class RouteInspection : CustomAnnotatedInspection(
    "Route",
    "de.craftsblock.craftsnet.api.http.annotations.Route",
    DynamicUrlParamInspectionRule(),
    ParameterInspectionRule(0, "exchange", "de.craftsblock.craftsnet.api.http.Exchange"),
    RequireImplementationInspectionRule("de.craftsblock.craftsnet.api.http.RequestHandler"),
    TransformerInspectionRule(),
)