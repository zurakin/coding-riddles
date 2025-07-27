export interface UserProfile {
  username: string;
  solvedRiddlesCount: number;
}
export interface Riddle {
  id: number;
  title: string;
  description: string;
  code: string;
  testCases: TestCase[];
  validationCode: string;
  functionName?: string;
  author?: Author;
  createdAt?: string;
  solutionsCount?: number;
  completedByCurrentUser?: boolean;
}

export interface TestCase {
  input: string;
  output: string;
}

export interface Author {
  username: string;
}