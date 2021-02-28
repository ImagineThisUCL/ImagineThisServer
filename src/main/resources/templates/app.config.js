const projectName = process.env.PROJECT_NAME;
const projectSlug = process.env.PROJECT_ID;

export default {
    "expo": {
        "name": projectName,
        "slug": projectSlug,
        "owner": "imaginethis", // Expo publishes app under imaginethis organization account
        "description": "An automatically generated app with ImagineThis.",
        "version": "1.0.0",
        "orientation": "portrait"
    }
}
