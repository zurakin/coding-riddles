export class JavaScriptExecutor {
    execute(code: string): string {
        try {
            const result = eval(code);
            console.log("Output:", result);
            return String(result);
        } catch (error) {
            console.error("Execution Error:", error);
            return `Execution Error: ${error instanceof Error ? error.message : String(error)}`;
        }
    }
}
