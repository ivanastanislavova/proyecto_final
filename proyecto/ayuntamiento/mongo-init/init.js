db = db.getSiblingDB("ayuntamiento_db");

db.aggregatedData.insertOne({
  timeStamp: new Date(),
  aggregatedData: [
    {
      id: 1,
      averageBikesAvailable: 4.5,
      airQuality: {
        nitricOxides: 10.5,
        nitrogenDioxides: 5.2,
        VOCs_NMHC: 6.8,
        PM2_5: 7.1
      }
    },
    {
      id: 2,
      averageBikesAvailable: 8.2,
      airQuality: {
        nitricOxides: 12.0,
        nitrogenDioxides: 6.4,
        VOCs_NMHC: 9.3,
        PM2_5: 11.2
      }
    }
  ]
});
