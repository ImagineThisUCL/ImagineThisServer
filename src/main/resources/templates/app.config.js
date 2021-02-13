const projectName = process.env.PROJECT_NAME;
const projectSlug = projectName.toLowerCase().replace(/ /g, "-");

export default {
    "expo": {
        "name": projectName,
        "slug": projectSlug,
        "description": "An automatically generated app with ImagineThis.",
        "version": "1.0.0",
        "orientation": "portrait"
    }
}
