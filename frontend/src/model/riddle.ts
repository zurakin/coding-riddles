export interface Riddle {
  id: number;
  title: string;
  description: string;
  code: string;
  testCases: TestCase[];
  validationCode: string;
  functionName?: string;
}

export interface TestCase {
  id: number;
  input: string;
  output: string;
}