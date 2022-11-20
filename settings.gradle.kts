rootProject.name = "housepit-server"
include(
    // service module
    "housepit-admin-server",
    "housepit-api-server",

    // common module
    "housepit-core",
    "housepit-domain",

    // infra module
    "infra:rdbms",

    // client module
    "client:auth",
)
