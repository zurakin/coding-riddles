export interface Riddle {
  id: number;
  title: string;
  description: string;
  code: string;
  testCases: TestCase[];
  validationCode: string;
  functionName?: string;
  author?: Author;
}

export interface TestCase {
  input: string;
  output: string;
}

export interface Author {
  username: string;
}