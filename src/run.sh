gcloud functions deploy function-tl-ukr-bot --entry-point es.tl.ukr.bot.menu.CloudFunctionMenu --runtime java17 --region europe-west1 --trigger-http --memory 256MB --allow-unauthenticated --project tl-bot-spain --set-build-env-vars=JAVA_TOOL_OPTIONS=-Dfile.encoding="UTF-8"