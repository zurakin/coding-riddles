export interface Riddle {
  id: number;
  title: string;
  description: string;
  code: string;
  testCases: {
      id: number;
      input: string;
      output: string;
  }[];
  validationCode: string;
}