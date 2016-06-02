package io.collectx.mobilevoter.models


case class HtmlMsg(data: String)

case class IpInfo(query: String, country: Option[String], city: Option[String], lat: Option[Double], lon: Option[Double])

case class IpPairSummaryRequest(ip1: String, ip2: String)