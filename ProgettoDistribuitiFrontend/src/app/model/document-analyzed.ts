import { AnalyzeSentimentSuccessResult } from "@azure/ai-text-analytics";

export interface DocumentAnalyzed{

  id: number;
  documentSentiments: AnalyzeSentimentSuccessResult[];
  confidenceScores: {
    "positive": number;
    "neutre": number;
    "negative": number;
    "id": number;
  };
  numberDocs: number;
  date: Date;

}
