# @capacitor-mlkit/document-scanner

Capacitor plugin for ML Kit Document Scanner.

## Installation

```bash
npm install @capacitor-mlkit/document-scanner
npx cap sync
```

## Usage

```typescript
import { DocumentScanner } from '@capacitor-mlkit/document-scanner';

const echo = async () => {
  await DocumentScanner.echo();
};
```

## API

<docgen-index>

* [`echo(...)`](#echo)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------

</docgen-api>
